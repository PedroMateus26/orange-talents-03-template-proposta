package com.zupacademy.MicroservicoPropota.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;

import java.net.Socket;

public class SituacaoNumeroCartaoHealthIndicator implements HealthIndicator, HealthContributor {

    private static final String URL = "http://localhost:8888/api/contas";

    @Override
    public Health health() {
        try (Socket socket =
                     new Socket(new java.net.URL(URL).getHost(), 8888)) {

        } catch (Exception e) {
            //log.warn("Failed to connect to: {}",URL);
            return Health.down()
                    .withDetail("Erro",HttpStatus.valueOf(500))
                    .build();
        }
        return Health.up().withDetail("Sucesso",HttpStatus.valueOf(200)).build();
    }

    public static String getURL() {
        return URL;
    }
}
