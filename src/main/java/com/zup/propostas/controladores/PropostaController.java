package com.zup.propostas.controladores;

import com.zup.propostas.dtos.request.PropostaRequest;
import com.zup.propostas.modelos.Proposta;
import com.zup.propostas.repositorios.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;

    @Autowired
    public PropostaController(PropostaRepository propostaRepository){
        this.propostaRepository = propostaRepository;
    }


    /* ----------- */

    @PostMapping
    public ResponseEntity<Void> salvarNovaProposta(@RequestBody @Valid PropostaRequest formulario){
        System.out.println(formulario);
        Proposta proposta = formulario.toModelo();
        propostaRepository.save(proposta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }



}
