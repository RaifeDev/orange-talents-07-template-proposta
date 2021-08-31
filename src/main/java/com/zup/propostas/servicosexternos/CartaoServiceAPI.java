package com.zup.propostas.servicosexternos;

import com.zup.propostas.dtos.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao-api", url = "${service.cartao}")
public interface CartaoServiceAPI {

    @GetMapping
    CartaoResponse buscarCartaoParaPropostaElegivel(@RequestParam String idProposta);


}
