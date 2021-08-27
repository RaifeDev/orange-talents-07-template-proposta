package com.zup.propostas.dtos.response;

import com.zup.propostas.modelos.Endereco;
import com.zup.propostas.modelos.EstadoProposta;
import com.zup.propostas.modelos.Proposta;

import java.math.BigDecimal;

public class PropostaResponse {

    private Long id;

    private String email;

    private String nome;

    private String documento;

    private Endereco endereco;

    private BigDecimal salario;

    private EstadoProposta statusDaProposta;


    @Deprecated
    public PropostaResponse(){

    }


    public PropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.documento = proposta.getDocumento();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.statusDaProposta = proposta.getStatusDaProposta();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EstadoProposta getStatusDaProposta() {
        return statusDaProposta;
    }



}
