package com.zup.propostas.dtos.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class EnderecoRequest {

    @NotBlank
    private String logradouro;

    @NotBlank
    private String estado;

    @NotBlank
    @Length(max = 2, min = 2)
    private String uf;


    @Deprecated
    public EnderecoRequest(){

    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getEstado() {
        return estado;
    }

    public String getUf() {
        return uf;
    }
}
