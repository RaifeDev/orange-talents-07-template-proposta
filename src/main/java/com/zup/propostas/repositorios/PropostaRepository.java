package com.zup.propostas.repositorios;

import com.zup.propostas.modelos.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {


    Optional<Proposta> findByDocumento(String documento);



}
