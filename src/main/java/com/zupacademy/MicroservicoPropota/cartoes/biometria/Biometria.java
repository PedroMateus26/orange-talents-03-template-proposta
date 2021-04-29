package com.zupacademy.MicroservicoPropota.cartoes.biometria;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Biometria {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String biometria;
    @CreationTimestamp
    private LocalDateTime criadoEm=LocalDateTime.now();

    public Biometria(String biometria) {
        this.biometria = biometria;

    }

    public Long getId() {
        return id;
    }

    public String getBiometria() {
        return biometria;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
