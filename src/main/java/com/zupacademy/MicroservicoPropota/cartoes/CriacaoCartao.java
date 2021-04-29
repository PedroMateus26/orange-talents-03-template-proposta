package com.zupacademy.MicroservicoPropota.cartoes;

import com.zupacademy.MicroservicoPropota.clientes.VerificaRestricaoFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cartoes", url = "${endereco.associa-proposta}")
public interface CriacaoCartao {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<CartaoCriadoResponse> verificaNumeroCartao(VerificaRestricaoFeignRequest verificaRestricaoFeignRequest);
}
