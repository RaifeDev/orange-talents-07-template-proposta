package com.zup.propostas.servicosexternos;

import com.zup.propostas.dtos.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao-api", url = "http://localhost:8888/api/")
public interface CartaoServiceAPI {

    @GetMapping("cartoes")
    CartaoResponse buscarCartaoParaPropostaElegivel(@RequestParam String idProposta);


}
