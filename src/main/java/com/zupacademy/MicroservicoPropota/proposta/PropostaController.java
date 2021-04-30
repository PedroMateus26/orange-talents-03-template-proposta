package com.zupacademy.MicroservicoPropota.proposta;
import com.zupacademy.MicroservicoPropota.clientes.SituacaoDoCartao;
import com.zupacademy.MicroservicoPropota.validadores.ValidaDadosDeServicosExteriores;
import com.zupacademy.MicroservicoPropota.validadores.exception_handler.ApiErroException;
import com.zupacademy.MicroservicoPropota.proposta.dtos.PropostaRequestDTO;
import com.zupacademy.MicroservicoPropota.proposta.dtos.PropostaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/propostas")
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

    @GetMapping("/{id}")
    public ResponseEntity<?> retornaPropostaPeloId(@PathVariable Long id){
        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(()->new ApiErroException(HttpStatus
                        .valueOf(404),"Proposta não encontrada para o id: "+id));
        PropostaResponseDTO propostaResponseDTO= new PropostaResponseDTO(proposta,proposta.getSituacaoDoCartao());
        return ResponseEntity.ok().body(propostaResponseDTO);
    }

}
