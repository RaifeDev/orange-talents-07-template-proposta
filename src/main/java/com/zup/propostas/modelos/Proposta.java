package com.zup.propostas.modelos;

import com.zup.propostas.dtos.response.CartaoResponse;
import com.zup.propostas.validacoes.CpfCnpj;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_propostas")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    @CpfCnpj
    private String documento;

    @OneToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private Endereco endereco;

    @Positive
    @NotNull
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private EstadoProposta statusDaProposta;

    @OneToOne(cascade = CascadeType.MERGE)
    private Cartao cartao;


    @Deprecated
    public Proposta(){}

    public Proposta(String email, String nome, Endereco endereco, BigDecimal salario, String documento) {
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.documento = documento;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public EstadoProposta getStatusDaProposta() {
        return statusDaProposta;
    }

    public void statusDaProposta(EstadoProposta statusDaProposta) {
        this.statusDaProposta = statusDaProposta;
    }

    public void associarCartao(Cartao cartao){
        this.cartao = cartao;
    }



}
