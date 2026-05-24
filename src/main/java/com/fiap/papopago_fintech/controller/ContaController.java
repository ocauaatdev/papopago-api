package com.fiap.papopago_fintech.controller;

import com.fiap.papopago_fintech.dto.conta.RequestContaDTO;
import com.fiap.papopago_fintech.dto.conta.RequestUpdateContaDTO;
import com.fiap.papopago_fintech.dto.conta.ResponseContaDTO;
import com.fiap.papopago_fintech.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseContaDTO> cadastrarConta(@Valid @RequestBody RequestContaDTO dto){
        ResponseContaDTO response = service.cadastrarConta(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseContaDTO> obterContaPorId(@PathVariable Long id) {
        ResponseContaDTO response = service.obterContaPorId(id);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/minhas-contas/{idUsuario}")
    public ResponseEntity<List<ResponseContaDTO>> obterMinhasContas(@PathVariable Long idUsuario) {
        List<ResponseContaDTO> response = service.obterMinhasContas(idUsuario);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseContaDTO> atualizarConta(@PathVariable Long id, @Valid @RequestBody RequestUpdateContaDTO dto) {
        ResponseContaDTO response = service.atualizarConta(id, dto);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        service.deletarConta(id);
        return ResponseEntity.status(204).build();
    }
}
