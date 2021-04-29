package com.zupacademy.MicroservicoPropota.proposta;

import com.zupacademy.MicroservicoPropota.cartoes.Cartao;
import com.zupacademy.MicroservicoPropota.clientes.SituacaoDoCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);

    List<Proposta> findAllByCartaoIdIsNullAndSituacaoDoCartao(SituacaoDoCartao situacaoDoCartao);
    Cartao findByCartaoId(String id);
}
