package com.zupacademy.MicroservicoPropota.cartoes.biometria;

import com.zupacademy.MicroservicoPropota.cartoes.Cartao;
import com.zupacademy.MicroservicoPropota.proposta.PropostaRepository;
import com.zupacademy.MicroservicoPropota.validadores.exception_handler.ApiErroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/biometrias")
public class BiometriaController {

    @PersistenceContext
    private EntityManager entityManager;

    private PropostaRepository propostaRepository;

    public BiometriaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> associaBiometria(@PathVariable String id, @RequestBody @Valid  BiometriaRequestDTO biometriaRequestDTO){
        biometriaRequestDTO.verificaBase64();

        String jpql="select 1 from Biometria where biometria=:biometria";
        Query query=entityManager.createQuery(jpql)
                .setParameter("biometria",biometriaRequestDTO.getBiometria());
        Cartao cartao=entityManager.find(Cartao.class, id);
        Biometria biometria=biometriaRequestDTO.convertToBiometria();

        if(cartao==null) {
            throw new ApiErroException(HttpStatus.NOT_FOUND, "Não foi encontrado nenhum cartão com o identificador fornecido");
        }else if(!query.getResultList().isEmpty()){
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Essa biometria jpa foi cadastrado!");
        }
        cartao.adicionaBiometria(biometria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(biometriaRequestDTO);

    }
}
