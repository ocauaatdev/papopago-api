package com.fiap.papopago_fintech.dto.instFin;

import com.fiap.papopago_fintech.entity.InstituicaoFinanceira;

public record ResponseInstFinDTO(
        Long id,
        String nome,
        String codigoBacen) {

    public ResponseInstFinDTO(InstituicaoFinanceira instituicaoFinanceira) {
        this(
                instituicaoFinanceira.getId(),
                instituicaoFinanceira.getNome(),
                instituicaoFinanceira.getCodigoBacen()
        );
    }
}
