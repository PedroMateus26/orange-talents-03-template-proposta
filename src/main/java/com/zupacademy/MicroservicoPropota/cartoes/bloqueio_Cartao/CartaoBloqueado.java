package com.zupacademy.MicroservicoPropota.cartoes.bloqueio_Cartao;

public enum CartaoBloqueado {
    NÃO,SIM;

    public CartaoBloqueado normaliza(boolean bloqueado){
        if(true){
           return CartaoBloqueado.SIM;
        }else{
            return CartaoBloqueado.NÃO;
        }
    }
}
