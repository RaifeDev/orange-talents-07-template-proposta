package com.zup.propostas.dtos.response;

import com.zup.propostas.modelos.Cartao;

import java.math.BigDecimal;

public class CartaoResponse {

    private String id;

    private String emitidoEm;

    private String titular;

    private Integer limite;

    private String idProposta;



    @Deprecated
    public CartaoResponse(){

    }

    public CartaoResponse(String id, String emitidoEm, String titular, Integer limite, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
    }


    public String getId() {
        return id;
    }

    public String getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public String getIdProposta() {
        return idProposta;
    }


    public Cartao toModel() {

        return new Cartao(id, emitidoEm, titular);

    }

    @Override
    public String toString() {
        return "CartaoResponse{" +
                "id='" + id + '\'' +
                ", emitidoEm='" + emitidoEm + '\'' +
                ", titular='" + titular + '\'' +
                ", idProposta='" + idProposta + '\'' +
                '}';
    }
}
