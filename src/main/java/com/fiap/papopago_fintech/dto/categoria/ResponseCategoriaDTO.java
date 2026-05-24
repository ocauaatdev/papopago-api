package com.fiap.papopago_fintech.dto.categoria;

import com.fiap.papopago_fintech.entity.Categoria;

public record ResponseCategoriaDTO(
        Long id,
        String nome,
        String tipo
) {
    public ResponseCategoriaDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getNomeCategoria(), categoria.getTipo());
    }
}
