package com.zup.propostas.modelos;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_carteiras")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCarteira statusCarteira;

    @ManyToOne
    @NotNull
    private Cartao cartao;


    @Deprecated
    public Carteira(){}

    public Carteira(TipoCarteira tipoCarteira, Cartao cartao) {
        this.tipoCarteira = tipoCarteira;
        this.statusCarteira = statusCarteira;
    }


    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public StatusCarteira getStatusCarteira() {
        return statusCarteira;
    }

    public Long getId() {
        return id;
    }
}
