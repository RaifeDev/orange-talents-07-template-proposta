package com.zup.propostas.controladores;

import com.zup.propostas.dtos.request.*;
import com.zup.propostas.modelos.*;
import com.zup.propostas.repositorios.AvisoViagemRepository;
import com.zup.propostas.repositorios.BloqueioCartaoRepository;
import com.zup.propostas.repositorios.CartaoRepository;
import com.zup.propostas.repositorios.CarteiraRepository;
import com.zup.propostas.servicosexternos.CartaoServiceAPI;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    private final CartaoRepository cartaoRepository;

    private final BloqueioCartaoRepository bloqueioCartaoRepository;

    private final CartaoServiceAPI cartaoServiceAPI;

    private final AvisoViagemRepository avisoViagemRepository;

    private final CarteiraRepository carteiraRepository;

    @Autowired
    public CartaoController(CartaoRepository cartaoRepository, BloqueioCartaoRepository bloqueioCartaoRepository,
                            CartaoServiceAPI cartaoServiceAPI, AvisoViagemRepository avisoViagemRepository,
                            CarteiraRepository carteiraRepository) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioCartaoRepository = bloqueioCartaoRepository;
        this.cartaoServiceAPI = cartaoServiceAPI;
        this.avisoViagemRepository = avisoViagemRepository;
        this.carteiraRepository = carteiraRepository;
    }


    @PostMapping("{idCartao}/bloqueio")
    public ResponseEntity<?> bloquearCartao(@PathVariable Long idCartao, HttpServletRequest request){

        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getHeader("X-FORWARDED-FOR");

        if(possivelCartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if(ip == null){
            request.getRemoteAddr();
        }

        Cartao cartao = possivelCartao.get();

        if(cartao.getStatusCartao().equals(StatusCartao.BLOQUEADO)){
            return ResponseEntity.unprocessableEntity().build();
        }

        BloqueioCartao bloqueioCartao = new BloqueioCartao(ip, userAgent, cartao);

        try{
            OrdemBloqueioRequest ordemBloqueioRequest = new OrdemBloqueioRequest("ms-propostas");
            cartaoServiceAPI.ordemParaBloquearCartao(cartao.getNumeroCartao(), ordemBloqueioRequest);
            cartao.bloquearCartao();
        }catch (FeignException exception){
            System.out.println("exception: " + exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        bloqueioCartaoRepository.save(bloqueioCartao);
        cartaoRepository.save(cartao);
        return ResponseEntity.ok().build();

    }

    @PostMapping("{idCartao}/avisoviagem")
    public ResponseEntity<?> avisoViagem(@PathVariable("idCartao") Long idCartao,
                                         @RequestBody AvisoViagemRequest formulario, HttpServletRequest request){

        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getHeader("X-FORWARDED-FOR");


        if(possivelCartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if(ip == null){
            request.getRemoteAddr();
        }

        Cartao cartao = possivelCartao.get();

        if(cartao.getStatusCartao().equals(StatusCartao.BLOQUEADO)){
            return ResponseEntity.unprocessableEntity().build();
        }

        try{
            OrdemAvisoViagemRequest ordemAvisoViagemRequest =
                    new OrdemAvisoViagemRequest(formulario.getDestinoDaViagem(), formulario.getTerminoDaViagem());
            cartaoServiceAPI.ordemParaAvisoViagem(cartao.getNumeroCartao(), ordemAvisoViagemRequest);
        }catch (FeignException exception){
            System.out.println("exception: " + exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        AvisoViagem avisoViagem = formulario.converterParaModelo(cartao, ip, userAgent);
        avisoViagemRepository.save(avisoViagem);

        return ResponseEntity.ok().build();
    }

    @PostMapping("{idCartao}/carteiras")
    public ResponseEntity<?> associarCarteira(@PathVariable Long idCartao, @RequestBody @Valid CarteiraRequest carteiraRequest){

        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);

        if(possivelCartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = possivelCartao.get();
        List<Carteira> carteiraAssociada = carteiraRepository.findByStatusAndCartaoNumeroCartao(StatusCarteira.ATIVO, cartao.getNumeroCartao());

        for(Carteira carteiraEncontrada: carteiraAssociada){
            if(carteiraEncontrada.getTipoCarteira() == carteiraRequest.getTipoCarteira()){
                return ResponseEntity.unprocessableEntity().build();
            }
        }

        Carteira carteira = carteiraRequest.toModel(cartao);

        try{
            OrdemCarteiraRequest ordemCarteiraRequest = new OrdemCarteiraRequest(carteiraRequest.getEmail(), carteiraRequest.getTipoCarteira());
            cartaoServiceAPI.associaCarteira(cartao.getNumeroCartao(), ordemCarteiraRequest);
        }catch (FeignException e){
            System.out.println("excecao: "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        carteiraRepository.save(carteira);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(carteira.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }




}
