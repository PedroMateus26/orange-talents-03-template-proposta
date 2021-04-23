package com.zupacademy.MicroservicoPropota.validators.cpf_cnpj;

import com.zupacademy.MicroservicoPropota.proposta.dtos.PropostaRequestDTO;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class ValidatorSequence implements DefaultGroupSequenceProvider<PropostaRequestDTO> {

    @Override
    public List<Class<?>> getValidationGroups(PropostaRequestDTO propostaRequestDTO) {
        List<Class<?>> groups=new ArrayList<>();
        groups.add(PropostaRequestDTO.class);
        if(propostaRequestDTO!=null){
            Integer pessoaFisica=14;
            Integer pessoaJuridica=18;
            if(pessoaFisica.equals(propostaRequestDTO.getCpf_cnpj().length())){
                groups.add(PessoaFisica.class);
            }if(pessoaJuridica.equals(propostaRequestDTO.getCpf_cnpj().length())){
                groups.add(PessoaJuridica.class);
            }
        }
        return groups;
    }
}
