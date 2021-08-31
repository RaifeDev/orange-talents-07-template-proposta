package com.zup.propostas.controladores;

import com.zup.propostas.dtos.request.BiometriaRequest;
import com.zup.propostas.modelos.Biometria;
import com.zup.propostas.modelos.Cartao;
import com.zup.propostas.repositorios.BiometriaRepository;
import com.zup.propostas.repositorios.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/biometria")
public class BiometriaController {

    private final BiometriaRepository biometriaRepository;

    private final CartaoRepository cartaoRepository;

    @Autowired
    public BiometriaController(BiometriaRepository biometriaRepository, CartaoRepository cartaoRepository) {
        this.biometriaRepository = biometriaRepository;
        this.cartaoRepository = cartaoRepository;
    }


    @PostMapping("/{idCartao}")
    public ResponseEntity<?> cadastrarBiometria(@RequestBody @Valid BiometriaRequest formulario,
                                                   @PathVariable Long idCartao){

        Optional<Cartao> cartao = cartaoRepository.findById(idCartao);

        if(cartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        try{
            Base64.getDecoder().decode(formulario.getBiometriaDedo());
            Biometria biometria = new Biometria(formulario.getBiometriaDedo(), cartao.get());
            biometriaRepository.save(biometria);
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }



}
