package com.fiap.papopago_fintech.repository;

import com.fiap.papopago_fintech.entity.Categoria;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNomeCategoria(String nome);
}
