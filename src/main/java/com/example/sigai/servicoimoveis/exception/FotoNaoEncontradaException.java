package com.example.sigai.servicoimoveis.exception;

public class FotoNaoEncontradaException extends RuntimeException {
    public FotoNaoEncontradaException(Long id) {
        super("Foto não encontrada com ID: " + id);
    }
}
