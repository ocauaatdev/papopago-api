package com.fiap.papopago_fintech.dto.instFin;

import jakarta.validation.constraints.NotBlank;

public record RequestCreateInstFinDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Código do Bacen é obrigatório")
        String codigoBacen
) {
}
