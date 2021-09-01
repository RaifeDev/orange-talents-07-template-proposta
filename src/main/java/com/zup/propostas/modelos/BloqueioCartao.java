package com.zup.propostas.modelos;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_bloqueios_cartao")
public class BloqueioCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bloqueadoEm;

    @NotBlank
    private String ipOrigemCliente;

    @NotBlank
    private String userAgent;

    @ManyToOne
    @NotNull
    private Cartao cartao;


    @Deprecated
    public BloqueioCartao(){

    }

    public BloqueioCartao(String ipOrigemCliente, String userAgent, Cartao cartao) {
        this.bloqueadoEm = LocalDateTime.now();
        this.ipOrigemCliente = ipOrigemCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }





}
