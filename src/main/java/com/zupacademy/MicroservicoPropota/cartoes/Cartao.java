package com.zupacademy.MicroservicoPropota.cartoes;

import com.zupacademy.MicroservicoPropota.cartoes.biometria.Biometria;
import com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao.BloqueioCartao;
import com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao.CartaoBloqueado;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(nullable = false)
    private String idCartao;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Biometria> biometrias= new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private BloqueioCartao bloqueioCartao;

    public Cartao() {
    }

    public Cartao(String idCartao) {

        this.idCartao = idCartao;
        this.bloqueioCartao = new BloqueioCartao(CartaoBloqueado.NÃO);
    }


    public String getId() {
        return id;
    }

    public String getIdCartao() {
        return idCartao;
    }

    public List<Biometria> getBiometrias() {
        return biometrias;
    }

    public BloqueioCartao getBloqueioCartao() {
        return bloqueioCartao;
    }

    public void adicionaBiometria(Biometria biometria){
        this.biometrias.add(biometria);
    }

}
