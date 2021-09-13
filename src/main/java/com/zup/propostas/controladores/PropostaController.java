package com.zup.propostas.controladores;

import com.zup.propostas.dtos.request.AnaliseCartaoRequest;
import com.zup.propostas.dtos.request.PropostaRequest;
import com.zup.propostas.dtos.response.AnaliseCartaoResponse;
import com.zup.propostas.dtos.response.PropostaResponse;
import com.zup.propostas.modelos.EstadoProposta;
import com.zup.propostas.modelos.Proposta;
import com.zup.propostas.repositorios.PropostaRepository;
import com.zup.propostas.servicosexternos.AnaliseCartao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("api/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;

    private final AnaliseCartao analiseCartao;

    @Autowired
    public PropostaController(PropostaRepository propostaRepository, AnaliseCartao analiseCartao){
        this.propostaRepository = propostaRepository;
        this.analiseCartao = analiseCartao;
    }


    /* ----------- */

    @PostMapping
    public ResponseEntity<Void> salvarNovaProposta(@RequestBody @Valid PropostaRequest formulario){

        Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(formulario.getDocumento());

        if(possivelProposta.isPresent()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Proposta proposta = formulario.toModelo();
        propostaRepository.save(proposta);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri();

        try{
            AnaliseCartaoResponse resultadoDaAnalise = analiseCartao.solicitacaoAnalise(new AnaliseCartaoRequest(proposta));
            proposta.statusDaProposta(EstadoProposta.ELEGIVEL);
        }catch (FeignException e){
            proposta.statusDaProposta(EstadoProposta.NAO_ELEGIVEL);
        }

        propostaRepository.save(proposta);
        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> buscarProposta(@PathVariable Long id){

        Optional<Proposta> possivelProposta = propostaRepository.findById(id);

        if(possivelProposta.isPresent()){
            PropostaResponse propostaResponse = new PropostaResponse(possivelProposta.get());
            return ResponseEntity.ok().body(propostaResponse);
        }

        return ResponseEntity.notFound().build();
    }




}
