package com.zup.propostas.repositorios;

import com.zup.propostas.modelos.Carteira;
import com.zup.propostas.modelos.StatusCarteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {



    List<Carteira> findByStatusAndCartaoNumeroCartao(StatusCarteira ativo, String numeroCartao);



}
