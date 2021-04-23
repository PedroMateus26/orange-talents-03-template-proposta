package com.zupacademy.MicroservicoPropota.clientes;

public enum ResultadoSolicitacao {
    COM_RESTRICAO,SEM_RESTRICAO;

   public SituacaoDoCartao normaliza(){
        if(this.equals(COM_RESTRICAO)){
            return SituacaoDoCartao.NAO_ELEGIVEL;
        }else{
            return SituacaoDoCartao.ELEGIVEL;
        }
   }
}
