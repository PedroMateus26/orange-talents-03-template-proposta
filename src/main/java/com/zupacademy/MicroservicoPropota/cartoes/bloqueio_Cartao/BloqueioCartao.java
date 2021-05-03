package com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BloqueioCartao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String ipCliente;
    private String userAgent;
    @Enumerated(EnumType.STRING)
    private CartaoBloqueado cartaoBloqueado;

    public BloqueioCartao() {
    }

    public BloqueioCartao(CartaoBloqueado cartaoBloqueado) {
        this.cartaoBloqueado = CartaoBloqueado.NÃO;
    }

    public UUID getId() {
        return id;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public CartaoBloqueado getCartaoBloqueado() {
        return cartaoBloqueado;
    }

    public void atualizaBloqueioCartao(String ip, String userAgent) {
        this.cartaoBloqueado = cartaoBloqueado.normaliza(true);
        this.ipCliente = ip;
        this.userAgent = userAgent;
    }
}
