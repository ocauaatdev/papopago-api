package com.fiap.papopago_fintech.exception;

public class TransacaoNaoEncontradaException extends RuntimeException {
    public TransacaoNaoEncontradaException(String message) {
        super(message);
    }
}
