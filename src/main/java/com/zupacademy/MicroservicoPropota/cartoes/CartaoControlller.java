package com.zupacademy.MicroservicoPropota.cartoes;

import com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao.BloqueioCartaoResponse;
import com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao.CartaoBloqueado;
import com.zupacademy.MicroservicoPropota.cartoes.requisicoes_feign.CriacaoCartao;
import com.zupacademy.MicroservicoPropota.validadores.exception_handler.ApiErroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoControlller {

    private CartaoRespository cartaoRespository;

    private CriacaoCartao criacaoCartao;

    public CartaoControlller(CartaoRespository cartaoRespository, CriacaoCartao cartao) {
        this.cartaoRespository = cartaoRespository;
        this.criacaoCartao = cartao;
    }

    @PostMapping("/bloqueio/{id}")
    @Transactional
    public ResponseEntity<?> bloquearCartao(@PathVariable("id") String idCartao, HttpServletRequest request) {

        Cartao cartao = cartaoRespository.findByidCartao(idCartao)
                .orElseThrow(()-> new ApiErroException(HttpStatus.NOT_FOUND,"Cartão não encontrado"));

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        if(cartao.getBloqueioCartao().getCartaoBloqueado().equals(CartaoBloqueado.SIM)){
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY,"Esse cartão já está bloqueado!");
        }


        ResponseEntity<?> resultado = criacaoCartao.cartaoBloquado(cartao.getIdCartao(),new SistemaResponsavel());
        if(!resultado.getStatusCode().is2xxSuccessful()){
           throw new ApiErroException(HttpStatus.BAD_REQUEST,"Não foi possível bloquear o cartão, algum serviço está fora do ar.");
        }
        cartao.getBloqueioCartao().atualizaBloqueioCartao(ip,userAgent);
        cartaoRespository.save(cartao);
        return ResponseEntity.ok().body(new BloqueioCartaoResponse());

    }
}
