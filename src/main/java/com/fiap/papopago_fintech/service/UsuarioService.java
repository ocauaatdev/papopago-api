package com.fiap.papopago_fintech.service;

import com.fiap.papopago_fintech.dto.usuario.RequestCreateUsuarioDTO;
import com.fiap.papopago_fintech.dto.usuario.ResponseUsuarioDTO;
import com.fiap.papopago_fintech.dto.usuario.UpdateRequestUsuarioDTO;
import com.fiap.papopago_fintech.entity.Usuario;
import com.fiap.papopago_fintech.exception.EmailCadastradoException;
import com.fiap.papopago_fintech.exception.RegraNegocioException;
import com.fiap.papopago_fintech.exception.UsuarioNaoEncontradoException;
import com.fiap.papopago_fintech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public ResponseUsuarioDTO cadastrarUsuario(RequestCreateUsuarioDTO dto){
        if(repository.existsByEmail(dto.email())){
            throw new EmailCadastradoException("Email já cadastrado");
        }

//        Convertendo a data de nascimento de String para LocalDate
        LocalDate dataNascimento;

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            dataNascimento = LocalDate.parse(dto.dataNascimento(), formatador);
        } catch (Exception e) {
            throw new RegraNegocioException("Data de nascimento inválida. Use o formato DD-MM-YYYY.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setDataNascimento(dataNascimento);
        usuario.setNumeroTelefone(dto.numeroTelefone());
        usuario.setCpf(dto.cpf());
        usuario.setSenha(dto.senha());
        repository.save(usuario);

        return new ResponseUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                usuario.getNumeroTelefone(),
                usuario.getCpf()
        );
    }

    public ResponseUsuarioDTO obterUsuarioPorId(Long id){
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));

        return new ResponseUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                usuario.getNumeroTelefone(),
                usuario.getCpf()
        );
    }

    public ResponseUsuarioDTO atualizarUsuario(Long id, UpdateRequestUsuarioDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));

        // Se o email passado no dto não for igual do usuario encontrado, verifica se esse email passado no dto já existe no banco de dados, se existir lança a exceção de email já cadastrado
        if (!usuario.getEmail().equals(dto.email()) && repository.existsByEmail(dto.email())) {
            throw new EmailCadastradoException("Email já cadastrado");
        }

        LocalDate dataNascimento;
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            dataNascimento = LocalDate.parse(dto.dataNascimento(), formatador);
        } catch (Exception e) {
            throw new RegraNegocioException("Data de nascimento inválida. Use o formato DD-MM-YYYY.");
        }

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setDataNascimento(dataNascimento);
        usuario.setNumeroTelefone(dto.numeroTelefone());
        usuario.setCpf(dto.cpf());
        repository.save(usuario);

        return new ResponseUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                usuario.getNumeroTelefone(),
                usuario.getCpf()
        );
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
        repository.delete(usuario);
    }
}

