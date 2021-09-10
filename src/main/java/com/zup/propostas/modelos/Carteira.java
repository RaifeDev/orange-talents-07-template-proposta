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
    private StatusCarteira status;

    @ManyToOne
    @NotNull
    private Cartao cartao;


    @Deprecated
    public Carteira(){}

    public Carteira(TipoCarteira tipoCarteira, Cartao cartao) {
        this.tipoCarteira = tipoCarteira;
        this.status = StatusCarteira.ATIVO;
        this.cartao = cartao;
    }


    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public Long getId() {
        return id;
    }
}
