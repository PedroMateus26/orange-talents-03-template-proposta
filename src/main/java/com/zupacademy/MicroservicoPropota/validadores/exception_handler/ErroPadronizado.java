package com.zupacademy.MicroservicoPropota.validadores.exception_handler;

import java.util.ArrayList;
import java.util.Collection;

public class ErroPadronizado {
    private Collection<String> mensagens=new ArrayList<>();

    public ErroPadronizado() {
    }

    public ErroPadronizado(Collection<String> mensagens) {
        this.mensagens = mensagens;
    }

    public Collection<String> getMensagens() {
        return mensagens;
    }
}
