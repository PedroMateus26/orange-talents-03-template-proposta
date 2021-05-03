package com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao;

public class BloqueioCartaoResponse {
    private String message;

    public BloqueioCartaoResponse() {
        this.message = "Cartão bloqueado com sucesso!";
    }

    public String getMessage() {
        return message;
    }
}
