package com.fiap.papopago_fintech.repository;

import com.fiap.papopago_fintech.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByUsuarioId(Long idUsuario);
}
