package com.fiap.papopago_fintech.dto.conta;

import com.fiap.papopago_fintech.entity.Conta;
import java.math.BigDecimal;

public record ResponseContaDTO(
        Long id,
        String nomeConta,
        BigDecimal saldo,
        UsuarioResumoDTO usuario,
        BankResumoDTO instituicaoFinanceira
) {

    // Construtor que recebe a entidade Conta e mapeia para o DTO
    public ResponseContaDTO(Conta conta) {
        this(
                conta.getId(),
                conta.getNomeConta(),
                conta.getSaldo(),


                new UsuarioResumoDTO(
                        conta.getUsuario().getId(),
                        conta.getUsuario().getNome()
                ),


                new BankResumoDTO(
                        conta.getInstFin().getId(),
                        conta.getInstFin().getNome(),
                        conta.getInstFin().getCodigoBacen()
                )
        );
    }
}

// Records auxiliares
record UsuarioResumoDTO(Long id, String nome) {}
record BankResumoDTO(Long id, String nome, String codigoBacen) {}