package com.zupacademy.MicroservicoPropota.proposta;

import com.zupacademy.MicroservicoPropota.cartoes.Cartao;
import com.zupacademy.MicroservicoPropota.clientes.SituacaoDoCartao;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Proposta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String documento;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private SituacaoDoCartao situacaoDoCartao;

    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String nome, String email, String documento, String endereco, BigDecimal salario) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
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

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public SituacaoDoCartao getSituacaoDoCartao() {
        return situacaoDoCartao;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void atualizaSituacaoCartao(SituacaoDoCartao situacaoDoCartao){
        this.situacaoDoCartao=situacaoDoCartao;
    }

    public void associaNumeroCartao(String idCartao){
        this.cartao=new Cartao(idCartao);
    }
}
