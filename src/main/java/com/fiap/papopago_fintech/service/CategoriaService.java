package com.fiap.papopago_fintech.service;

import com.fiap.papopago_fintech.dto.categoria.RequestCreateCategoriaDTO;
import com.fiap.papopago_fintech.dto.categoria.ResponseCategoriaDTO;
import com.fiap.papopago_fintech.entity.Categoria;
import com.fiap.papopago_fintech.exception.CategoriaNaoEncontradaException;
import com.fiap.papopago_fintech.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public ResponseCategoriaDTO novaCategoria(RequestCreateCategoriaDTO dto){
        if (repository.existsByNomeCategoria(dto.nome())) {
            throw new RuntimeException("Já existe uma categoria com esse nome.");
        }

        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(dto.nome());
        categoria.setTipo(dto.tipo().toUpperCase());
        repository.save(categoria);

        return new ResponseCategoriaDTO(categoria);
    }

    public List<ResponseCategoriaDTO> listarCategorias(){
        List<Categoria> categorias = repository.findAll();
        return categorias.stream().
                map(c -> new ResponseCategoriaDTO(c))
                .toList();
    }

    public ResponseCategoriaDTO buscarCategoriaPorId(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com o ID: " + id));
        return new ResponseCategoriaDTO(categoria);
    }

    public void deletarCategoria(Long id) {
        if (!repository.existsById(id)) {
            throw new CategoriaNaoEncontradaException("Categoria não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }

    public ResponseCategoriaDTO atualizarCategoria(Long id, RequestCreateCategoriaDTO dto) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com o ID: " + id));

        if (repository.existsByNomeCategoria(dto.nome()) && !categoria.getNomeCategoria().equals(dto.nome())) {
            throw new RuntimeException("Já existe uma categoria com esse nome.");
        }

        categoria.setNomeCategoria(dto.nome());
        categoria.setTipo(dto.tipo().toUpperCase());
        repository.save(categoria);

        return new ResponseCategoriaDTO(categoria);
    }
}
