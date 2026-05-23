package com.fiap.papopago_fintech.controller;

import com.fiap.papopago_fintech.dto.usuario.RequestCreateUsuarioDTO;
import com.fiap.papopago_fintech.dto.usuario.ResponseUsuarioDTO;
import com.fiap.papopago_fintech.dto.usuario.UpdateRequestUsuarioDTO;
import com.fiap.papopago_fintech.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseUsuarioDTO> cadastrarUsuario(@Valid @RequestBody RequestCreateUsuarioDTO dto){
        ResponseUsuarioDTO response = service.cadastrarUsuario(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> obterUsuarioPorId(@PathVariable Long id) {
        ResponseUsuarioDTO response = service.obterUsuarioPorId(id);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UpdateRequestUsuarioDTO dto) {
        ResponseUsuarioDTO response = service.atualizarUsuario(id, dto);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
