package com.zup.propostas.modelos;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String biometriaDedo;

    @NotNull
    @OneToOne
    private Cartao cartao;


    @Deprecated
    public Biometria(){

    }

    public Biometria(String biometriaDedo, Cartao cartao) {
        this.biometriaDedo = biometriaDedo;
        this.cartao = cartao;
    }




}
