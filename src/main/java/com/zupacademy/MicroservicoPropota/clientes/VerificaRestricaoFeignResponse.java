package com.zupacademy.MicroservicoPropota.clientes;

public class VerificaRestricaoFeignResponse {

    private String documento;
    private String nome;
    private SituacaoDoCartao situacaoDoCartao;
    private String idProposta;

    public VerificaRestricaoFeignResponse(String documento, String nome, ResultadoSolicitacao resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.situacaoDoCartao = resultadoSolicitacao.normaliza();
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public SituacaoDoCartao getSituacaoDoCartao() {
        return situacaoDoCartao;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
