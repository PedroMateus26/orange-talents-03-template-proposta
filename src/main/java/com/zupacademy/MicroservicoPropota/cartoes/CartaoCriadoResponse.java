package com.zupacademy.MicroservicoPropota.cartoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class CartaoCriadoResponse {

    private String id;

    @Deprecated
    public CartaoCriadoResponse() {
    }

    public CartaoCriadoResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}


