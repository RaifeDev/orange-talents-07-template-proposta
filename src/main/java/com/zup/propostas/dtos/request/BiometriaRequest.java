package com.zup.propostas.dtos.request;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {


    @NotBlank
    private String biometriaDedo;



    @Deprecated
    public BiometriaRequest(){

    }

    public BiometriaRequest(String biometriaDedo) {
        this.biometriaDedo = biometriaDedo;
    }


    public String getBiometriaDedo() {
        return biometriaDedo;
    }



}
