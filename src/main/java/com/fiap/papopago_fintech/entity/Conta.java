package com.fiap.papopago_fintech.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "T_PP_CONTA")
public class Conta {

    @Id
    @Column(name = "id_conta") //
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_conta", nullable = false, length = 60)
    private String nomeConta;

    @Column(name = "nr_saldo", nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bank", nullable = false)
    private InstituicaoFinanceira instFin;

    public Conta() {
    }

    public Conta(Long id, String nomeConta, BigDecimal saldo, Usuario usuario, InstituicaoFinanceira instFin) {
        this.id = id;
        this.nomeConta = nomeConta;
        this.saldo = saldo;
        this.usuario = usuario;
        this.instFin = instFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeConta() {
        return nomeConta;
    }

    public void setNomeConta(String nomeConta) {
        this.nomeConta = nomeConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public InstituicaoFinanceira getInstFin() {
        return instFin;
    }

    public void setInstFin(InstituicaoFinanceira instFin) {
        this.instFin = instFin;
    }
}