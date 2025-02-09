package com.zupacademy.MicroservicoPropota.validadores.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class MeuHandlerAdvice {

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadronizado> handler(MethodArgumentNotValidException methodArgumentNotValidException){
       Collection<String> mensagens=new ArrayList<>();
       BindingResult bindingResult=methodArgumentNotValidException.getBindingResult();
       List<FieldError> fieldErrorList=bindingResult.getFieldErrors();
       fieldErrorList.forEach(fieldError -> {
           String mensagem = String.format("Campo %s: %s",fieldError.getField(), fieldError.getDefaultMessage());
           mensagens.add(mensagem);
       });
       ErroPadronizado erroPadronizado=new ErroPadronizado(mensagens);
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);
   }
    @ExceptionHandler(ApiErroException.class)
    public ResponseEntity<ErroPadronizado> handlerApiException(ApiErroException apiErroException){
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(apiErroException.getReason());

        ErroPadronizado erroPadronizado= new ErroPadronizado(mensagens);
        return ResponseEntity.status(apiErroException.getHttpStatus()).body(erroPadronizado);
    }
}
