package com.zupacademy.MicroservicoPropota.config;

import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Component("MeuApp")
public class MeuAppHealthContributor implements CompositeHealthContributor {

    private Map<String,HealthContributor> contribuitors= new LinkedHashMap<>();

    public MeuAppHealthContributor(SituacaoClienteHealthIndicator situacaoCliente) {
        contribuitors.put("Situação Cliente", situacaoCliente);
    }

    @Override
    public Iterator<NamedContributor<HealthContributor>> iterator() {
        return contribuitors.entrySet().stream()
                .map(elem->NamedContributor.of(elem.getKey(),elem.getValue()))
                .iterator();
    }

    @Override
    public HealthContributor getContributor(String name) {
        return contribuitors.get(name);
    }
}
