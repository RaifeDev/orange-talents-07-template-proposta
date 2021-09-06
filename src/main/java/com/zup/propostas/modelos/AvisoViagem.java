package com.zup.propostas.modelos;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "tb_avisos_de_viagens")
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String destinoDaViagem;

    @Future
    @NotNull
    private LocalDate terminoDaViagem;

    @NotBlank
    private String ipOrigem;

    @NotBlank
    private String userAgent;

    @ManyToOne
    private Cartao cartao;


    @Deprecated
    public AvisoViagem(){

    }

    public AvisoViagem(String destinoDaViagem, LocalDate terminoDaViagem, String ipOrigem, String userAgent, Cartao cartao) {
        this.destinoDaViagem = destinoDaViagem;
        this.terminoDaViagem = terminoDaViagem;
        this.ipOrigem = ipOrigem;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }
}
