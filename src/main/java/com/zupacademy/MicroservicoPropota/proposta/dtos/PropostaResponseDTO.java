package com.zupacademy.MicroservicoPropota.proposta.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zupacademy.MicroservicoPropota.clientes.SituacaoDoCartao;
import com.zupacademy.MicroservicoPropota.proposta.Proposta;

public class PropostaResponseDTO {
    private String nome;
    private String email;
    private SituacaoDoCartao situacaoDoCartao;
    private String mensagemDeEspera;

    public PropostaResponseDTO(Proposta proposta,SituacaoDoCartao situacaoDoCartao) {
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.situacaoDoCartao=proposta.getSituacaoDoCartao();
    }

    public PropostaResponseDTO(Proposta proposta) {
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.mensagemDeEspera="Em análise";
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SituacaoDoCartao getSituacaoDoCartao(){
        return situacaoDoCartao;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMensagemDeEspera() {
        return mensagemDeEspera;
    }
}
