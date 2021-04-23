package com.zupacademy.MicroservicoPropota.proposta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf_cnpj;
    private String endereco;
    private BigDecimal salario;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String nome, String email, String cpf_cnpj, String endereco, BigDecimal salario) {
        this.nome = nome;
        this.email = email;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
