package com.zup.propostas.dtos.request;

import javax.validation.constraints.NotBlank;

public class OrdemBloqueioRequest {

    @NotBlank
    private String sistemaResponsavel;


    public OrdemBloqueioRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

}
