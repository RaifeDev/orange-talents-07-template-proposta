package com.zup.propostas.dtos.request;


import com.zup.propostas.modelos.Endereco;
import com.zup.propostas.modelos.Proposta;
import com.zup.propostas.validacoes.CpfCnpj;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {

    @NotBlank
    @CpfCnpj
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @Positive
    @NotNull
    private BigDecimal salario;

    @NotNull
    private EnderecoRequest endereco;


    @Deprecated
    public PropostaRequest(){

    }


    public Proposta toModelo() {
        Endereco endereco = new Endereco(this.endereco);
        return new Proposta(email, nome, endereco, salario, documento);
    }


    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EnderecoRequest getEndereco() {
        return endereco;
    }
}
