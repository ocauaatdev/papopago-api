package com.fiap.papopago_fintech.dto.usuario;

import java.time.LocalDate;

public record ResponseUsuarioDTO(
        Long id,
        String nome,
        String email,
        LocalDate dataNascimento,
        String numeroTelefone,
        String cpf
) {
}
