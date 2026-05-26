package com.fiap.papopago_fintech.repository;

import com.fiap.papopago_fintech.entity.Conta;
import com.fiap.papopago_fintech.entity.Transacao;
import com.fiap.papopago_fintech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {


    List<Transacao> findByConta(Conta conta);

    List<Transacao> findByUsuario(Usuario usuario);
}
