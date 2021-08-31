package com.zup.propostas.modelos;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCartao;

    private String emitidoEm;

    @NotBlank
    private String titular;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @Deprecated
    public Cartao(){

    }

    public Cartao(String numeroCartao, String emitidoEm, String titular) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.statusCartao = StatusCartao.ATIVO;
    }





}
