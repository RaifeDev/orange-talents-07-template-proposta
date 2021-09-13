package com.zup.propostas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class PropostaNotValidException extends RuntimeException {
    public PropostaNotValidException(String message) {
        super(message);
    }
}