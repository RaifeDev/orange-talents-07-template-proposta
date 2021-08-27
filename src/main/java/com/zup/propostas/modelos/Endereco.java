package com.zup.propostas.modelos;

import com.zup.propostas.dtos.request.EnderecoRequest;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String estado;

    @NotBlank
    @Length(max = 2, min = 2)
    private String uf;




    @Deprecated
    public Endereco(){

    }

    public Endereco(EnderecoRequest endereco) {
        this.logradouro = endereco.getLogradouro();
        this.estado = endereco.getEstado();
        this.uf = endereco.getUf();
    }


    public Long getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getEstado() {
        return estado;
    }

    public String getUf() {
        return uf;
    }
}
