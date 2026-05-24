package com.fiap.papopago_fintech.dto.usuario;

import com.fiap.papopago_fintech.entity.Usuario;

import java.time.LocalDate;

public record ResponseUsuarioDTO(
        Long id,
        String nome,
        String email,
        LocalDate dataNascimento,
        String numeroTelefone,
        String cpf
) {
    public ResponseUsuarioDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                usuario.getNumeroTelefone(),
                usuario.getCpf()
        );
    }
}
