package com.fiap.papopago_fintech.service;

import com.fiap.papopago_fintech.dto.instFin.RequestCreateInstFinDTO;
import com.fiap.papopago_fintech.dto.instFin.ResponseInstFinDTO;
import com.fiap.papopago_fintech.entity.InstituicaoFinanceira;
import com.fiap.papopago_fintech.exception.InstFinanceiraNaoEncontradaException;
import com.fiap.papopago_fintech.exception.RegraNegocioException;
import com.fiap.papopago_fintech.repository.InstituicaoFinanceiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstFinanceiraService {

    @Autowired
    private InstituicaoFinanceiraRepository repository;

    public ResponseInstFinDTO cadastrarInstituicaoFinanceira(RequestCreateInstFinDTO dto){

        if (repository.existsByCodigoBacen(dto.codigoBacen())) {
            throw new RegraNegocioException("Código do Bacen já existe");
        }
        if (repository.existsByNome(dto.nome())) {
            throw new RegraNegocioException("Nome da instituição financeira já em uso");
        }

        InstituicaoFinanceira instituicaoFinanceira = new InstituicaoFinanceira();
        instituicaoFinanceira.setNome(dto.nome());
        instituicaoFinanceira.setCodigoBacen(dto.codigoBacen());
        repository.save(instituicaoFinanceira);

        return new ResponseInstFinDTO(instituicaoFinanceira);

    }

    public ResponseInstFinDTO buscarInstituicaoFinanceiraPorId(Long id) {
        InstituicaoFinanceira instituicaoFinanceira = repository.findById(id)
                .orElseThrow(() -> new InstFinanceiraNaoEncontradaException("Instituição financeira não encontrada"));

        return new ResponseInstFinDTO(instituicaoFinanceira);
    }

    public List<ResponseInstFinDTO> listarInstituicoesFinanceiras() {
        List<InstituicaoFinanceira> instituicoes = repository.findAll();
        return instituicoes.stream()
                .map(inst -> new ResponseInstFinDTO(inst))
                .toList();
    }

    public ResponseInstFinDTO atualizarInstituicaoFinanceira(Long id, RequestCreateInstFinDTO dto) {
        InstituicaoFinanceira instituicaoFinanceira = repository.findById(id)
                .orElseThrow(() -> new InstFinanceiraNaoEncontradaException("Instituição financeira não encontrada"));

        if (repository.existsByCodigoBacen(dto.codigoBacen()) && !instituicaoFinanceira.getCodigoBacen().equals(dto.codigoBacen())) {
            throw new RegraNegocioException("Código do Bacen já existe");
        }
        if (repository.existsByNome(dto.nome()) && !instituicaoFinanceira.getNome().equals(dto.nome())) {
            throw new RegraNegocioException("Nome da instituição financeira já em uso");
        }

        instituicaoFinanceira.setNome(dto.nome());
        instituicaoFinanceira.setCodigoBacen(dto.codigoBacen());
        repository.save(instituicaoFinanceira);

        return new ResponseInstFinDTO(instituicaoFinanceira);
    }

    public void deletarInstituicaoFinanceira(Long id) {
        InstituicaoFinanceira instituicaoFinanceira = repository.findById(id)
                .orElseThrow(() -> new InstFinanceiraNaoEncontradaException("Instituição financeira não encontrada"));
        repository.delete(instituicaoFinanceira);
    }
}
