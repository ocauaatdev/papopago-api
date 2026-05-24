package com.fiap.papopago_fintech.controller;

import com.fiap.papopago_fintech.dto.transacao.RequestTransacaoDTO;
import com.fiap.papopago_fintech.dto.transacao.ResponseTransacaoDTO;
import com.fiap.papopago_fintech.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @PostMapping("/nova")
    public ResponseEntity<ResponseTransacaoDTO> novaTransacao(@Valid @RequestBody RequestTransacaoDTO dto){
        ResponseTransacaoDTO response = service.novaTransacao(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<ResponseTransacaoDTO>> minhasTransacoes(@PathVariable Long idConta) {
        List<ResponseTransacaoDTO> transacoes = service.listarTransacoesPorConta(idConta);
        return ResponseEntity.status(200).body(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTransacaoDTO> obterTransacaoPorId(@PathVariable Long id) {
        ResponseTransacaoDTO transacao = service.obterTransacaoPorId(id);
        return ResponseEntity.status(200).body(transacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        service.excluirTransacao(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTransacaoDTO> atualizarTransacao(@PathVariable Long id, @Valid @RequestBody RequestTransacaoDTO dto) {
        ResponseTransacaoDTO transacaoAtualizada = service.atualizarTransacao(id, dto);
        return ResponseEntity.status(200).body(transacaoAtualizada);
    }

}
