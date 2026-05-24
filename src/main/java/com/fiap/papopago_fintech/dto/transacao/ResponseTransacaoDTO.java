package com.fiap.papopago_fintech.dto.transacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiap.papopago_fintech.dto.categoria.ResponseCategoriaDTO;
import com.fiap.papopago_fintech.dto.conta.ResponseContaDTO;
import com.fiap.papopago_fintech.entity.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseTransacaoDTO(
        Long id,
        String descricao,
        BigDecimal valor,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,

        String origem,
        ResponseContaDTO conta,
        ResponseCategoriaDTO categoria
) {
    // Construtor que recebe a entidade Transacao e mapeia para o DTO
    public ResponseTransacaoDTO(Transacao transacao) {
        this(
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getData(),
                transacao.getOrigem(),
                new ResponseContaDTO(transacao.getConta()),
                new ResponseCategoriaDTO(transacao.getCategoria())
        );
    }

}
