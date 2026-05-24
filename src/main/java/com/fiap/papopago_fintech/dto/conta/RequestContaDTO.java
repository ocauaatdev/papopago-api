package com.fiap.papopago_fintech.dto.conta;

import com.fiap.papopago_fintech.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RequestContaDTO(

        @NotBlank(message = "O nome da conta é obrigatório")
        String nomeConta,

        @NotNull(message = "O saldo é obrigatório")
        @PositiveOrZero(message = "O saldo deve ser um valor positivo ou zero")
        BigDecimal saldo,

        @NotNull(message = "O ID do usuário é obrigatório")
        Long idUsuario,

        @NotNull(message = "O ID do banco é obrigatório")
        Long idBank
) {
}
