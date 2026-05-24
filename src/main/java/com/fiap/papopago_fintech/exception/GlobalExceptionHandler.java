package com.fiap.papopago_fintech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailCadastradoException.class)
    private ResponseEntity<RestErrorMessage> handleEmailCadastradoException(EmailCadastradoException ex) {
        RestErrorMessage errorMessage = new RestErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RegraNegocioException.class)
    private ResponseEntity<RestErrorMessage> handleRegraNegocioException(RegraNegocioException ex) {
        RestErrorMessage errorMessage = new RestErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    private ResponseEntity<RestErrorMessage> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        RestErrorMessage errorMessage = new RestErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(InstFinanceiraNaoEncontradaException.class)
    private ResponseEntity<RestErrorMessage> handleInstFinanceiraNaoEncontradaException(InstFinanceiraNaoEncontradaException ex) {
        RestErrorMessage errorMessage = new RestErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    private ResponseEntity<RestErrorMessage> handleContaNaoEncontradaException(ContaNaoEncontradaException ex) {
        RestErrorMessage errorMessage = new RestErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    private ResponseEntity<RestErrorMessage> handleCategoriaNaoEncontradaException(CategoriaNaoEncontradaException ex) {
        RestErrorMessage errorMessage = new RestErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
