package org.axelgutierrez.piapoo2025.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<String> manejoRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservaInvalidaException.class)
    public ResponseEntity<String> manejoReservaInvalida(ReservaInvalidaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //Manejar errores de validación lanzados por @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errores = new StringBuilder(); //concatenar los posibles errores
        ex.getBindingResult().getFieldErrors().forEach(error -> {errores.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");});
        return new ResponseEntity<>(errores.toString(), HttpStatus.BAD_REQUEST); //transformamos el StringBuilder a string y mostramos al cliente
    }
}
