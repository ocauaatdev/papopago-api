package com.fiap.papopago_fintech.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record RequestCreateCategoriaDTO(

        @NotBlank(message = "O nome da categoria é obrigatório")
        String nome,

        @NotBlank(message = "O tipo da categoria é obrigatório")
        String tipo
) {
}
