package com.fiap.papopago_fintech.controller;

import com.fiap.papopago_fintech.dto.instFin.RequestCreateInstFinDTO;
import com.fiap.papopago_fintech.dto.instFin.ResponseInstFinDTO;
import com.fiap.papopago_fintech.service.InstFinanceiraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inst-financeira")
public class InstFinanceiraController {

    @Autowired
    private InstFinanceiraService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseInstFinDTO> cadastrarInstFinanceira(@Valid @RequestBody RequestCreateInstFinDTO dto){
        ResponseInstFinDTO response = service.cadastrarInstituicaoFinanceira(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseInstFinDTO> buscarInstFinanceiraPorId(@PathVariable Long id) {
        ResponseInstFinDTO response = service.buscarInstituicaoFinanceiraPorId(id);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ResponseInstFinDTO>> listarInstituicoesFinanceiras() {
        return ResponseEntity.status(200).body(service.listarInstituicoesFinanceiras());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseInstFinDTO> atualizarInstFinanceira(@PathVariable Long id, @Valid @RequestBody RequestCreateInstFinDTO dto) {
        ResponseInstFinDTO response = service.atualizarInstituicaoFinanceira(id, dto);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInstFinanceira(@PathVariable Long id) {
        service.deletarInstituicaoFinanceira(id);
        return ResponseEntity.status(204).build();
    }
}
