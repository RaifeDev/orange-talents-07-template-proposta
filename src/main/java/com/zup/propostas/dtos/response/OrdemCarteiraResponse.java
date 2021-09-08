package com.zup.propostas.dtos.response;

public class OrdemCarteiraResponse {

    private String resultado;

    private String id;



    public OrdemCarteiraResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }


    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
