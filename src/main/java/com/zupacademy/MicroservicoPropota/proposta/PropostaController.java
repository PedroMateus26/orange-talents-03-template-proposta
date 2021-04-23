package com.zupacademy.MicroservicoPropota.proposta;

import com.zupacademy.MicroservicoPropota.clientes.SituacaoDoCartao;
import com.zupacademy.MicroservicoPropota.clientes.VerifcaRestricaoClient;
import com.zupacademy.MicroservicoPropota.clientes.VerificaRestricaoFeignRequest;
import com.zupacademy.MicroservicoPropota.clientes.VerificaRestricaoFeignResponse;
import com.zupacademy.MicroservicoPropota.exception_handler.ApiErroException;
import com.zupacademy.MicroservicoPropota.proposta.dtos.PropostaRequestDTO;
import com.zupacademy.MicroservicoPropota.proposta.dtos.PropostaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/propostas")
public class PropostaController {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private VerifcaRestricaoClient verifcaRestricaoClient;

    @PostMapping
    @Transactional
    public ResponseEntity<?> insereProposta(@RequestBody @Valid PropostaRequestDTO propostaRequest){

        Query query=entityManager
                .createQuery("select 1 from Proposta where documento = :documento")
                .setParameter("documento",propostaRequest.getDocumento());

        if(!query.getResultList().isEmpty()){
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY,"Essa proposta já existe na base de dados");
        }

        Proposta proposta=propostaRequest.convert();

        VerificaRestricaoFeignRequest verificaRestricaoFeignRequest=new VerificaRestricaoFeignRequest(proposta);
        ResponseEntity<VerificaRestricaoFeignResponse> verificaRestricaoFeignResponse = verifcaRestricaoClient.response(verificaRestricaoFeignRequest);
        SituacaoDoCartao situacaoDoCartao=verificaRestricaoFeignResponse.getBody().getSituacaoDoCartao();

        proposta.atualizaSituacaoCartao(situacaoDoCartao);
        entityManager.persist(proposta);
        PropostaResponseDTO responseDTO=new PropostaResponseDTO(proposta);
        return ResponseEntity.ok().body(responseDTO);
    }

}
