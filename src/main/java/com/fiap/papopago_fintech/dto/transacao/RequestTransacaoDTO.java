package com.fiap.papopago_fintech.dto.transacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RequestTransacaoDTO(
        @NotBlank(message = "A descrição da transação é obrigatória.")
        String descricao,

        @NotNull(message = "O valor da transação é obrigatório.")
        @PositiveOrZero(message = "O valor da transação deve ser positivo ou zero.")
        BigDecimal valor,

        @NotBlank(message = "A data da transação é obrigatória.")
        String data,

        @NotBlank(message = "A origem da transação é obrigatória.")
        String origem,

        @NotNull(message = "O ID do usuário é obrigatório.")
        Long idUsuario,

        @NotNull(message = "O ID da categoria é obrigatório.")
        Long idCategoria,

        @NotNull(message = "O ID da conta é obrigatório.")
        Long idConta
) {
}
