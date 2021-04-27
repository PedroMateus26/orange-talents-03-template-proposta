package com.zupacademy.MicroservicoPropota.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component("Situação Cliente")
public class SituacaoClienteHealthIndicator implements HealthIndicator, HealthContributor {

    private static final String URL = "http://localhost:9999/api/solicitacao";

    @Override
    public Health health() {
        //Checagem se o serviço é alcançavel
        try (Socket socket =
                     new Socket(new java.net.URL(URL).getHost(),9999)) {
        } catch (Exception e) {
            //log.warn("Failed to connect to: {}",URL);
            return Health.down()
                    .withDetail("Erro",HttpStatus.valueOf(500))
                    .build();
        }
        return Health.up().withDetail("Sucesso",HttpStatus.valueOf(200)).build();
    }
}
