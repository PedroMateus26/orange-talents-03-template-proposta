package com.zupacademy.MicroservicoPropota.proposta;
import com.zupacademy.MicroservicoPropota.clientes.SituacaoDoCartao;
import com.zupacademy.MicroservicoPropota.validadores.ValidaDadosDeServicosExteriores;
import com.zupacademy.MicroservicoPropota.validadores.exception_handler.ApiErroException;
import com.zupacademy.MicroservicoPropota.proposta.dtos.PropostaRequestDTO;
import com.zupacademy.MicroservicoPropota.proposta.dtos.PropostaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/propostas")
public class PropostaController {

    private PropostaRepository propostaRepository;
    private ValidaDadosDeServicosExteriores validaDadosDeServicosExteriores;

    public PropostaController(PropostaRepository propostaRepository, ValidaDadosDeServicosExteriores validaDadosDeServicosExteriores) {
        this.propostaRepository = propostaRepository;
        this.validaDadosDeServicosExteriores = validaDadosDeServicosExteriores;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> insereProposta(@RequestBody @Valid PropostaRequestDTO propostaRequest){

        Proposta proposta=propostaRequest.convert();

        if(propostaRepository.existsByDocumento(proposta.getDocumento())){
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY,"Essa proposta já existe na base de dados");
        }

        SituacaoDoCartao situacaoDoCartao=
                validaDadosDeServicosExteriores.verificaElegibilidadeDaProposta(proposta);
        proposta.atualizaSituacaoCartao(situacaoDoCartao);

        proposta=propostaRepository.save(proposta);
        PropostaResponseDTO responseDTO=new PropostaResponseDTO(proposta);
        return ResponseEntity.ok().body(responseDTO);
    }

}
