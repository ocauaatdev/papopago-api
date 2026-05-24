package com.fiap.papopago_fintech.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateRequestUsuarioDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        String email,

        @NotBlank(message = "Data de nascimento é obrigatória")
        String dataNascimento,

        @NotBlank(message = "Número de telefone é obrigatório")
        String numeroTelefone
) {
}
