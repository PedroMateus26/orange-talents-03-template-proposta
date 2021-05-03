package com.zupacademy.MicroservicoPropota.cartoes.requisicoes_feign;

import com.zupacademy.MicroservicoPropota.cartoes.CartaoCriadoResponse;
import com.zupacademy.MicroservicoPropota.cartoes.SistemaResponsavel;
import com.zupacademy.MicroservicoPropota.clientes.VerificaRestricaoFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "cartoes", url = "${endereco.associa-proposta}")
public interface CriacaoCartao {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<CartaoCriadoResponse> verificaNumeroCartao(VerificaRestricaoFeignRequest verificaRestricaoFeignRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/{id}/bloqueios")
    ResponseEntity<?> cartaoBloquado(@PathVariable String id,@RequestBody SistemaResponsavel sistemaResposavel);
}
