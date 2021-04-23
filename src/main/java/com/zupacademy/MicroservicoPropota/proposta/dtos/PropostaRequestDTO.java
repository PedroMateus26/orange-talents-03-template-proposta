package com.zupacademy.MicroservicoPropota.proposta.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zupacademy.MicroservicoPropota.proposta.Proposta;
import com.zupacademy.MicroservicoPropota.validators.cpf_cnpj.PessoaFisica;
import com.zupacademy.MicroservicoPropota.validators.cpf_cnpj.PessoaJuridica;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequestDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O email não pode estar em branco")
    @NotNull(message = "O email é obrigatório")
    @Email(message = "O email deve ter fomator válido")
    private String email;

    @NotBlank(message = "O cpf ou cnpj  não pode estar em branco")
    @NotNull(message = "O cpf ou cnpj é obrigatório")
    @CPF(groups = PessoaFisica.class)
    @CNPJ(groups = PessoaJuridica.class)
    private String cpf_cnpj;

    @NotBlank(message = "O endereço não pode estar em branco")
    @NotNull(message = "O endereço é obrigatório")
    private String endereco;

    @Positive(message = "O valor do salário")
    @NotNull(message = "O salário é obrigatório")
    private BigDecimal salario;

    public PropostaRequestDTO(String nome, String email, String cpf_cnpj, String endereco, BigDecimal salario) {
        this.nome = nome;
        this.email = email;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.salario = salario;
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

    public Proposta convert() {
        return new Proposta(
                this.nome,
                this.email,
                this.cpf_cnpj,
                this.endereco,
                this.salario
        );
    }
}
