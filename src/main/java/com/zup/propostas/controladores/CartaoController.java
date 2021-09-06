package com.zup.propostas.controladores;

import com.zup.propostas.dtos.request.AvisoViagemRequest;
import com.zup.propostas.dtos.request.OrdemAvisoViagemRequest;
import com.zup.propostas.dtos.request.OrdemBloqueioRequest;
import com.zup.propostas.modelos.AvisoViagem;
import com.zup.propostas.modelos.BloqueioCartao;
import com.zup.propostas.modelos.Cartao;
import com.zup.propostas.modelos.StatusCartao;
import com.zup.propostas.repositorios.AvisoViagemRepository;
import com.zup.propostas.repositorios.BloqueioCartaoRepository;
import com.zup.propostas.repositorios.CartaoRepository;
import com.zup.propostas.servicosexternos.CartaoServiceAPI;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    private final CartaoRepository cartaoRepository;

    private final BloqueioCartaoRepository bloqueioCartaoRepository;

    private final CartaoServiceAPI cartaoServiceAPI;

    private final AvisoViagemRepository avisoViagemRepository;

    @Autowired
    public CartaoController(CartaoRepository cartaoRepository, BloqueioCartaoRepository bloqueioCartaoRepository, CartaoServiceAPI cartaoServiceAPI, AvisoViagemRepository avisoViagemRepository) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioCartaoRepository = bloqueioCartaoRepository;
        this.cartaoServiceAPI = cartaoServiceAPI;
        this.avisoViagemRepository = avisoViagemRepository;
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




}
