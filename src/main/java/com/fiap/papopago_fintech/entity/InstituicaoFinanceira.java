package com.fiap.papopago_fintech.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "T_PP_BANK")
public class InstituicaoFinanceira {

    @Id
    @Column(name = "id_bank")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_bank", nullable = false, length = 60)
    private String nome;

    @Column(name = "cd_bacen", nullable = false, length = 3)
    private String codigoBacen;

    public InstituicaoFinanceira() {
    }

    public InstituicaoFinanceira(Long id, String nome, String codigoBacen) {
        this.id = id;
        this.nome = nome;
        this.codigoBacen = codigoBacen;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCodigoBacen() { return codigoBacen; }
    public void setCodigoBacen(String codigoBacen) { this.codigoBacen = codigoBacen; }
}