package com.fiap.papopago_fintech.service;

import com.fiap.papopago_fintech.dto.transacao.RequestTransacaoDTO;
import com.fiap.papopago_fintech.dto.transacao.ResponseTransacaoDTO;
import com.fiap.papopago_fintech.entity.Categoria;
import com.fiap.papopago_fintech.entity.Conta;
import com.fiap.papopago_fintech.entity.Transacao;
import com.fiap.papopago_fintech.exception.CategoriaNaoEncontradaException;
import com.fiap.papopago_fintech.exception.ContaNaoEncontradaException;
import com.fiap.papopago_fintech.exception.RegraNegocioException;
import com.fiap.papopago_fintech.exception.TransacaoNaoEncontradaException;
import com.fiap.papopago_fintech.repository.CategoriaRepository;
import com.fiap.papopago_fintech.repository.ContaRepository;
import com.fiap.papopago_fintech.repository.TransacaoRepository;
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
    private ContaRepository contaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ResponseTransacaoDTO novaTransacao(RequestTransacaoDTO dto){

        Conta conta = contaRepository.findById(dto.idConta())
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com ID: " + dto.idConta()));

        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + dto.idCategoria()));

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
        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        repository.save(transacao);

        return new ResponseTransacaoDTO(transacao);
    }

    public List<ResponseTransacaoDTO> listarTransacoesPorConta(Long idConta){
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com ID: " + idConta));

        List<Transacao> transacoes = repository.findByConta(conta);
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

        Conta conta = contaRepository.findById(dto.idConta())
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com ID: " + dto.idConta()));

        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + dto.idCategoria()));

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
        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        repository.save(transacao);

        return new ResponseTransacaoDTO(transacao);
    }
}
