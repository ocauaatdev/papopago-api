package com.fiap.papopago_fintech.service;

import com.fiap.papopago_fintech.dto.transacao.RequestTransacaoDTO;
import com.fiap.papopago_fintech.dto.transacao.ResponseTransacaoDTO;
import com.fiap.papopago_fintech.entity.Categoria;
import com.fiap.papopago_fintech.entity.Conta;
import com.fiap.papopago_fintech.entity.Transacao;
import com.fiap.papopago_fintech.entity.Usuario;
import com.fiap.papopago_fintech.exception.*;
import com.fiap.papopago_fintech.repository.CategoriaRepository;
import com.fiap.papopago_fintech.repository.ContaRepository;
import com.fiap.papopago_fintech.repository.TransacaoRepository;
import com.fiap.papopago_fintech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ContaRepository contaRepository;

    public ResponseTransacaoDTO novaTransacao(RequestTransacaoDTO dto){

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + dto.idUsuario()));

        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + dto.idCategoria()));

        Conta conta = contaRepository.findById(dto.idConta())
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com ID: " + dto.idConta()));

        LocalDate dataTransacao;
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            dataTransacao = LocalDate.parse(dto.data(), formatador);
        } catch (Exception e) {
            throw new RegraNegocioException("Data de transação inválida. Use o formato dd/MM/yyyy.");
        }

        Transacao transacao = new Transacao();
        transacao.setDescricao(dto.descricao());
        transacao.setValor(dto.valor());
        transacao.setData(dataTransacao);
        transacao.setOrigem(dto.origem());
        transacao.setUsuario(usuario);
        transacao.setCategoria(categoria);
        transacao.setConta(conta);
        repository.save(transacao);

        return new ResponseTransacaoDTO(transacao);
    }

    public List<ResponseTransacaoDTO> listarTransacoesPorConta(Long idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        List<Transacao> transacoes = repository.findByUsuario(usuario);
        return transacoes.stream()
                .map(t -> new ResponseTransacaoDTO(t))
                .toList();
    }

    public ResponseTransacaoDTO obterTransacaoPorId(Long idTransacao) {
        Transacao transacao = repository.findById(idTransacao)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada com ID: " + idTransacao));
        return new ResponseTransacaoDTO(transacao);
    }

    public void excluirTransacao(Long idTransacao) {
        Transacao transacao = repository.findById(idTransacao)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada com ID: " + idTransacao));
        repository.delete(transacao);
    }

    public ResponseTransacaoDTO atualizarTransacao(Long idTransacao, RequestTransacaoDTO dto) {
        Transacao transacao = repository.findById(idTransacao)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada com ID: " + idTransacao));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + dto.idUsuario()));

        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + dto.idCategoria()));

        Conta conta = contaRepository.findById(dto.idConta())
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com ID: " + dto.idConta()));

        LocalDate dataTransacao;
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            dataTransacao = LocalDate.parse(dto.data(), formatador);
        } catch (Exception e) {
            throw new RegraNegocioException("Data de transação inválida. Use o formato dd/MM/yyyy.");
        }

        transacao.setDescricao(dto.descricao());
        transacao.setValor(dto.valor());
        transacao.setData(dataTransacao);
        transacao.setOrigem(dto.origem());
        transacao.setUsuario(usuario);
        transacao.setCategoria(categoria);
        transacao.setConta(conta);
        repository.save(transacao);

        return new ResponseTransacaoDTO(transacao);
    }
}
