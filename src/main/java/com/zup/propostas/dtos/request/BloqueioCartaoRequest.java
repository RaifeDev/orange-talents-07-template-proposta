package com.zup.propostas.dtos.request;

public class BloqueioCartaoRequest {

    private String resultado;




    @Deprecated
    public BloqueioCartaoRequest() {
    }


    public BloqueioCartaoRequest(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }


}
