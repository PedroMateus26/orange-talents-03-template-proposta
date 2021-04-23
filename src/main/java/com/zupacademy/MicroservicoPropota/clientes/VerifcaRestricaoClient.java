package com.zupacademy.MicroservicoPropota.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "verificacoes",url = "http://localhost:9999/api/solicitacao")
public interface VerifcaRestricaoClient {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<VerificaRestricaoFeignResponse> response(VerificaRestricaoFeignRequest verificaRestricaoFeignRequest);

}


