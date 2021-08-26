package com.zup.propostas.servicosexternos;

import com.zup.propostas.dtos.request.AnaliseCartaoRequest;
import com.zup.propostas.dtos.response.AnaliseCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analise-cartao", url = "http://localhost:9999/api/")
public interface AnaliseCartao {


    @PostMapping("solicitacao")
    AnaliseCartaoResponse solicitacaoAnalise(@RequestBody AnaliseCartaoRequest request);


}
