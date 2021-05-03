package com.zupacademy.MicroservicoPropota.cartoes;

import com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao.BloqueioCartao;
import com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao.BloqueioCartaoResponse;
import com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao.CartaoBloqueado;
import com.zupacademy.MicroservicoPropota.validadores.exception_handler.ApiErroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoControlller {

    private CartaoRespository cartaoRespository;

    public CartaoControlller(CartaoRespository cartaoRespository) {
        this.cartaoRespository = cartaoRespository;
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

        cartao.getBloqueioCartao().atualizaBloqueioCartao(ip,userAgent);
        cartaoRespository.save(cartao);
        return ResponseEntity.ok().body(new BloqueioCartaoResponse());
    }
}
