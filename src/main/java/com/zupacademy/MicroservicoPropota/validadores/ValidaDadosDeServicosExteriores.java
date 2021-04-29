package com.zupacademy.MicroservicoPropota.validadores;

import com.zupacademy.MicroservicoPropota.cartoes.CartaoCriadoResponse;
import com.zupacademy.MicroservicoPropota.cartoes.CriacaoCartao;
import com.zupacademy.MicroservicoPropota.clientes.SituacaoDoCartao;
import com.zupacademy.MicroservicoPropota.clientes.VerifcaRestricaoClient;
import com.zupacademy.MicroservicoPropota.clientes.VerificaRestricaoFeignRequest;
import com.zupacademy.MicroservicoPropota.clientes.VerificaRestricaoFeignResponse;
import com.zupacademy.MicroservicoPropota.proposta.Proposta;
import com.zupacademy.MicroservicoPropota.proposta.PropostaRepository;
import com.zupacademy.MicroservicoPropota.validadores.exception_handler.ApiErroException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.Socket;
import java.util.List;

@Service
public class ValidaDadosDeServicosExteriores {

    private PropostaRepository propostaRepository;

    private VerifcaRestricaoClient verifcaRestricaoClient;

    private CriacaoCartao criacaoCartao;


    public ValidaDadosDeServicosExteriores(PropostaRepository propostaRepository, VerifcaRestricaoClient verifcaRestricaoClient, CriacaoCartao criacaoCartao) {
        this.propostaRepository = propostaRepository;
        this.verifcaRestricaoClient = verifcaRestricaoClient;
        this.criacaoCartao = criacaoCartao;
    }

    @Transactional
    public SituacaoDoCartao verificaElegibilidadeDaProposta(Proposta proposta){

            VerificaRestricaoFeignRequest verificaRestricaoFeignRequest = new VerificaRestricaoFeignRequest(proposta);
            ResponseEntity<VerificaRestricaoFeignResponse> verificaRestricaoFeignResponse = verifcaRestricaoClient.response(verificaRestricaoFeignRequest);
            SituacaoDoCartao situacaoDoCartao = verificaRestricaoFeignResponse.getBody().getSituacaoDoCartao();
            return situacaoDoCartao;
    }

    @Scheduled(fixedDelayString = "${periodicidade.executa-tarefa}")
    @Transactional
    public void verificaElegibilidade(){

        try {
            List<Proposta> propostas = propostaRepository
                    .findAllByNumeroCartaoIsNullAndSituacaoDoCartao(SituacaoDoCartao.ELEGIVEL);
            propostas.forEach(proposta -> {
                VerificaRestricaoFeignRequest verificaRestricaoFeignRequest = new VerificaRestricaoFeignRequest(proposta);
                ResponseEntity<CartaoCriadoResponse> idCartao = criacaoCartao.verificaNumeroCartao(verificaRestricaoFeignRequest);
                proposta.associaNumeroCartao(idCartao.getBody().getId());
                System.out.println(proposta.getNumeroCartao());
            });
        }catch (FeignException e){
            throw new ApiErroException(HttpStatus.BAD_REQUEST,
                    "Não foi possível conectar ao sistema. Erro: "+e.getMessage());
        }

    }
}