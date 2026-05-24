package com.fiap.papopago_fintech.repository;

import com.fiap.papopago_fintech.entity.InstituicaoFinanceira;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoFinanceiraRepository extends JpaRepository<InstituicaoFinanceira, Long> {
    boolean existsByNome (String nome);

    boolean existsByCodigoBacen(String codigoBacen);
}
