package com.fiap.papopago_fintech.controller;

import com.fiap.papopago_fintech.dto.categoria.RequestCreateCategoriaDTO;
import com.fiap.papopago_fintech.dto.categoria.ResponseCategoriaDTO;
import com.fiap.papopago_fintech.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping("/nova")
    public ResponseEntity<ResponseCategoriaDTO> novaCategoria(@Valid @RequestBody RequestCreateCategoriaDTO dto){
        ResponseCategoriaDTO response = service.novaCategoria(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ResponseCategoriaDTO>> listarCategorias(){
        List<ResponseCategoriaDTO> categorias = service.listarCategorias();
        return ResponseEntity.status(200).body(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCategoriaDTO> buscarCategoriaPorId(@PathVariable Long id) {
        ResponseCategoriaDTO categoria = service.buscarCategoriaPorId(id);
        return ResponseEntity.status(200).body(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        service.deletarCategoria(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCategoriaDTO> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody RequestCreateCategoriaDTO dto) {
        ResponseCategoriaDTO categoriaAtualizada = service.atualizarCategoria(id, dto);
        return ResponseEntity.status(200).body(categoriaAtualizada);
    }
}
