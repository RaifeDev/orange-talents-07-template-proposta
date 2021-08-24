package com.zup.propostas.exceptions;

import com.zup.propostas.dtos.response.FormHandlerError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FormHandlerError>> catchValidationError(MethodArgumentNotValidException exception, HttpServletRequest request){

        List<FormHandlerError> formHandlerErrors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(error ->{
            FormHandlerError formHandlerError = new FormHandlerError(HttpStatus.BAD_REQUEST.value(),
                    error.getField(), exception.getClass().toString(), error.getDefaultMessage());
            formHandlerErrors.add(formHandlerError);
        });

        return ResponseEntity.badRequest().body(formHandlerErrors);

    }


}
