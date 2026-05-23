package com.fiap.papopago_fintech.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Table(name = "T_PP_USUARIO")
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
//    @SequenceGenerator(name = "usuario_seq", sequenceName = "T_PP_USUARIO_id_usuario_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "nm_usuario", nullable = false, length = 60)
    private String nome;

    @Email(message = "Email deve ser válido")
    @Column(name = "eml_usuario", nullable = false, length = 100)
    private String email;

    @Column(name = "dt_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nr_telefone", nullable = false, length = 15)
    private String numeroTelefone;

    @CPF(message = "CPF deve ser válido")
    @Column(name = "nr_cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "sen_senha", nullable = false, length = 100)
    private String senha;


    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, LocalDate dataNascimento, String numeroTelefone, String cpf, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.numeroTelefone = numeroTelefone;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
