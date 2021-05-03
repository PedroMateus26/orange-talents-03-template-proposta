package com.zupacademy.MicroservicoPropota.cartoes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartaoRespository extends JpaRepository<Cartao, UUID> {
   Optional<Cartao> findByidCartao(String idCartao);
}
