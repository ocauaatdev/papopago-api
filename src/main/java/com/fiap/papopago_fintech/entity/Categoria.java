package com.fiap.papopago_fintech.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "T_PP_CATEGORIA") //
public class Categoria {

    @Id
    @Column(name = "id_categoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_categoria", nullable = false, length = 60)
    private String nomeCategoria;

    @Column(name = "ds_tipo_categoria", nullable = false, length = 60)
    private String tipo;

    public Categoria() {
    }

    public Categoria(Long id, String nomeCategoria, String tipo) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
