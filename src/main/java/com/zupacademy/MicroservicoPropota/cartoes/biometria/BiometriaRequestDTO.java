package com.zupacademy.MicroservicoPropota.cartoes.biometria;

import com.zupacademy.MicroservicoPropota.cartoes.Cartao;
import com.zupacademy.MicroservicoPropota.validadores.exception_handler.ApiErroException;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Base64;

public class BiometriaRequestDTO {

    @NotNull(message = "Nenhuma biometria foi detectada")
    @NotBlank(message = "Deve haver pelo menos uma biometria")
    private String biometria;

    public BiometriaRequestDTO() {
    }

    public BiometriaRequestDTO(String biometria) {
        this.biometria = biometria;
    }


    public String getBiometria() {
        return biometria;
    }


    public Biometria convertToBiometria() {
        return new Biometria(this.biometria);
    }

    public void verificaBase64() {
        try{
            Base64.getDecoder().decode(this.biometria);
        }catch(Exception e){
            throw new ApiErroException(HttpStatus.BAD_REQUEST,"Token inválido!");
        }
    }
}
