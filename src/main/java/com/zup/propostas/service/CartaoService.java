package com.zup.propostas.service;

import com.zup.propostas.dtos.response.CartaoResponse;
import com.zup.propostas.modelos.Cartao;
import com.zup.propostas.modelos.EstadoProposta;
import com.zup.propostas.modelos.Proposta;
import com.zup.propostas.repositorios.PropostaRepository;
import com.zup.propostas.servicosexternos.CartaoServiceAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoService {

    private PropostaRepository propostaRepository;

    private CartaoServiceAPI cartaoServiceAPI;

    private Logger logger = LoggerFactory.getLogger(CartaoService.class);

    @Autowired
    public CartaoService(PropostaRepository propostaRepository, CartaoServiceAPI cartaoServiceAPI) {
        this.propostaRepository = propostaRepository;
        this.cartaoServiceAPI = cartaoServiceAPI;
    }


    @Scheduled(fixedDelay = 60000)
    public void associarCartao(){

        List<Proposta> propostas = propostaRepository.findAll();

        for(Proposta proposta : propostas){

            if(proposta.getStatusDaProposta().equals(EstadoProposta.ELEGIVEL) && proposta.getCartao() == null){
                logger.info("Iniciando associação de cartão...");
                logger.info("Proposta de numero: " + proposta.getId() + " Iniciada." );
                CartaoResponse possivelCartao = cartaoServiceAPI.buscarCartaoParaPropostaElegivel(proposta.getId().toString());
                Cartao cartao = possivelCartao.toModel();
                proposta.associarCartao(cartao);
                propostaRepository.save(proposta);
                logger.info("Proposta de numero: " + proposta.getId() + " foi salva com sucesso.");
            }

        }

    }


}
