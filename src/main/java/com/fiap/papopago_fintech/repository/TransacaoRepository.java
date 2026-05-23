package com.fiap.papopago_fintech.repository;

import com.fiap.papopago_fintech.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
