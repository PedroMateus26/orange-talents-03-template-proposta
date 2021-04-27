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

        String URL = "http://localhost:9999/api/solicitacao";

        try(Socket socket =
                    new Socket(new java.net.URL(URL).getHost(),9999)) {

            VerificaRestricaoFeignRequest verificaRestricaoFeignRequest = new VerificaRestricaoFeignRequest(proposta);
            ResponseEntity<VerificaRestricaoFeignResponse> verificaRestricaoFeignResponse = verifcaRestricaoClient.response(verificaRestricaoFeignRequest);
            SituacaoDoCartao situacaoDoCartao = verificaRestricaoFeignResponse.getBody().getSituacaoDoCartao();
            return situacaoDoCartao;
        }catch (Exception e){
            throw new ApiErroException(HttpStatus.valueOf(400), "Erro ao tentar conectar ao serviço externo. Erro: "+e.getMessage());
        }
    }

    @Scheduled(fixedDelayString = "${periodicidade.executa-tarefa}")
    @Transactional
    public void verificaElegibilidade(){
        String URL = "http://localhost:8888/api/contas";
        try (Socket socket =
                     new Socket(new java.net.URL(URL).getHost(), 8888)) {

            List<Proposta> propostas = propostaRepository
                    .findAllByNumeroCartaoIsNullAndSituacaoDoCartao(SituacaoDoCartao.ELEGIVEL);
            propostas.forEach(proposta -> {
                VerificaRestricaoFeignRequest verificaRestricaoFeignRequest = new VerificaRestricaoFeignRequest(proposta);
                ResponseEntity<CartaoCriadoResponse> idCartao=criacaoCartao.verificaNumeroCartao(verificaRestricaoFeignRequest);
                proposta.associaNumeroCartao(idCartao.getBody().getId());
                System.out.println(proposta.getNumeroCartao());
            });
        } catch (Exception e) {
            throw new ApiErroException(HttpStatus.valueOf(400),"Erro ao tentar o serviço: "+e.getMessage());
        }

    }
}
