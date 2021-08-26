package com.zup.propostas.dtos.request;

import com.zup.propostas.modelos.Proposta;

public class AnaliseCartaoRequest {

    private String documento;

    private String nome;

    private String idProposta;


    @Deprecated
    public AnaliseCartaoRequest(){}

    public AnaliseCartaoRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

}
