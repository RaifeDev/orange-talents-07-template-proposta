package com.zup.propostas.servicosexternos;

import com.zup.propostas.dtos.request.AnaliseCartaoRequest;
import com.zup.propostas.dtos.response.AnaliseCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analise-solicitacao", url = "${service.analise.solicitacao}")
public interface AnaliseCartao {


    @PostMapping
    AnaliseCartaoResponse solicitacaoAnalise(@RequestBody AnaliseCartaoRequest request);


}
