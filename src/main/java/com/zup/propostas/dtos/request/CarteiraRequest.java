package com.zup.propostas.dtos.request;

import com.zup.propostas.modelos.Cartao;
import com.zup.propostas.modelos.Carteira;
import com.zup.propostas.modelos.TipoCarteira;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraRequest {

    @NotBlank
    private String email;

    @NotNull
    private TipoCarteira tipoCarteira;


    public CarteiraRequest(String email, TipoCarteira tipoCarteira) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public String getEmail() {
        return email;
    }

    public Carteira toModel(Cartao cartao){
        return new Carteira(tipoCarteira, cartao);
    }



}
