package com.zup.propostas.servicosexternos;

import com.zup.propostas.dtos.request.BloqueioCartaoRequest;
import com.zup.propostas.dtos.request.OrdemAvisoViagemRequest;
import com.zup.propostas.dtos.request.OrdemBloqueioRequest;
import com.zup.propostas.dtos.response.CartaoResponse;
import com.zup.propostas.dtos.response.OrdemAvisoViagemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cartao-api", url = "${service.cartao}")
public interface CartaoServiceAPI {

    @GetMapping
    CartaoResponse buscarCartaoParaPropostaElegivel(@RequestParam String idProposta);

    @PostMapping("{id}/bloqueios")
    BloqueioCartaoRequest ordemParaBloquearCartao(@PathVariable("id") String numeroCartao,
                                                  @RequestBody OrdemBloqueioRequest ordemBloqueioRequest);

    @PostMapping("{id}/avisos")
    OrdemAvisoViagemResponse ordemParaAvisoViagem(@PathVariable("id") String numeroCartao,
                                                  @RequestBody OrdemAvisoViagemRequest ordemAvisoViagemRequest);


}
