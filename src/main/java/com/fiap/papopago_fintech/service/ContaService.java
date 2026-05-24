package com.fiap.papopago_fintech.service;

import com.fiap.papopago_fintech.dto.conta.RequestContaDTO;
import com.fiap.papopago_fintech.dto.conta.RequestUpdateContaDTO;
import com.fiap.papopago_fintech.dto.conta.ResponseContaDTO;
import com.fiap.papopago_fintech.entity.Conta;
import com.fiap.papopago_fintech.entity.InstituicaoFinanceira;
import com.fiap.papopago_fintech.entity.Usuario;
import com.fiap.papopago_fintech.exception.ContaNaoEncontradaException;
import com.fiap.papopago_fintech.exception.InstFinanceiraNaoEncontradaException;
import com.fiap.papopago_fintech.exception.UsuarioNaoEncontradoException;
import com.fiap.papopago_fintech.repository.ContaRepository;
import com.fiap.papopago_fintech.repository.InstituicaoFinanceiraRepository;
import com.fiap.papopago_fintech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstituicaoFinanceiraRepository financeiraRepository;

    public ResponseContaDTO cadastrarConta(RequestContaDTO dto) {

        Usuario usuarioEncontrado = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + dto.idUsuario()));

        InstituicaoFinanceira instituicaoFinanceiraEncontrada = financeiraRepository.findById(dto.idBank())
                .orElseThrow(() -> new InstFinanceiraNaoEncontradaException("Instituição financeira não encontrada com o ID: " + dto.idBank()));


        Conta conta = new Conta();
        conta.setNomeConta(dto.nomeConta());
        conta.setSaldo(dto.saldo());
        conta.setUsuario(usuarioEncontrado);
        conta.setInstFin(instituicaoFinanceiraEncontrada);
        repository.save(conta);

        return new ResponseContaDTO(conta);
    }

    public ResponseContaDTO obterContaPorId(Long id) {
        Conta conta = repository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com o ID: " + id));
        return new ResponseContaDTO(conta);
    }

    public List<ResponseContaDTO> obterMinhasContas(Long idUsuario) {
        List<Conta> contas = repository.findByUsuarioId(idUsuario);

        return contas.stream()
                .map(conta -> new ResponseContaDTO(conta))
                .toList();
    }

    public ResponseContaDTO atualizarConta(Long id, RequestUpdateContaDTO dto) {
        Conta conta = repository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com o ID: " + id));

        InstituicaoFinanceira instituicaoFinanceiraEncontrada = financeiraRepository.findById(dto.idBank())
                .orElseThrow(() -> new InstFinanceiraNaoEncontradaException("Instituição financeira não encontrada com o ID: " + dto.idBank()));

        conta.setNomeConta(dto.nomeConta());
        conta.setSaldo(dto.saldo());
        conta.setInstFin(instituicaoFinanceiraEncontrada);
        repository.save(conta);

        return new ResponseContaDTO(conta);
    }

    public void deletarConta(Long id) {
        Conta conta = repository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com o ID: " + id));
        repository.delete(conta);
    }
}
