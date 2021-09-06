package com.zup.propostas.dtos.request;

import com.zup.propostas.modelos.AvisoViagem;
import com.zup.propostas.modelos.Cartao;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {


    @NotBlank
    private String destinoDaViagem;

    @Future
    @NotNull
    private LocalDate terminoDaViagem;


    @Deprecated
    public AvisoViagemRequest(){

    }


    public String getDestinoDaViagem() {
        return destinoDaViagem;
    }

    public LocalDate getTerminoDaViagem() {
        return terminoDaViagem;
    }

    public AvisoViagemRequest(String destinoDaViagem, LocalDate terminoDaViagem) {
        this.destinoDaViagem = destinoDaViagem;
        this.terminoDaViagem = terminoDaViagem;
    }


    public AvisoViagem converterParaModelo(Cartao cartao, String ip, String userAgent) {
        return new AvisoViagem(destinoDaViagem, terminoDaViagem, ip, userAgent, cartao);
    }


}
