package com.zup.propostas.controladores;

import com.zup.propostas.dtos.request.OrdemBloqueioRequest;
import com.zup.propostas.modelos.BloqueioCartao;
import com.zup.propostas.modelos.Cartao;
import com.zup.propostas.modelos.StatusCartao;
import com.zup.propostas.repositorios.BloqueioCartaoRepository;
import com.zup.propostas.repositorios.CartaoRepository;
import com.zup.propostas.servicosexternos.CartaoServiceAPI;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    private final CartaoRepository cartaoRepository;

    private final BloqueioCartaoRepository bloqueioCartaoRepository;

    private final CartaoServiceAPI cartaoServiceAPI;

    @Autowired
    public CartaoController(CartaoRepository cartaoRepository, BloqueioCartaoRepository bloqueioCartaoRepository, CartaoServiceAPI cartaoServiceAPI) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioCartaoRepository = bloqueioCartaoRepository;
        this.cartaoServiceAPI = cartaoServiceAPI;
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

        System.out.println(ip);
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



}
