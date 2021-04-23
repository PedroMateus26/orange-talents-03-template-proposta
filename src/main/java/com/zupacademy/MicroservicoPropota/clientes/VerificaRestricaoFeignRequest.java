package com.zupacademy.MicroservicoPropota.clientes;

import com.zupacademy.MicroservicoPropota.proposta.Proposta;
import org.springframework.http.ResponseEntity;

public class VerificaRestricaoFeignRequest {

    private String documento;
    private String nome;
    private Long idProposta;

    public VerificaRestricaoFeignRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
