package com.zupacademy.MicroservicoPropota.proposta.dtos;

import com.zupacademy.MicroservicoPropota.proposta.Proposta;

public class PropostaResponseDTO {
    private String nome;
    private String email;

    public PropostaResponseDTO(Proposta proposta) {
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
