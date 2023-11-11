package com.juliano.app.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.juliano.app.exceptions.CustomNotFoundException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({CustomNotAllowedException.class})
    public ResponseEntity<Object> customNotAllowedException(CustomNotAllowedException ex){
    	
    	System.out.println("[ControllerAdvice]: Called customNotAllowedException");
    	
    	return new ResponseEntity<>(RespostaPadrao.builder()
        		.mensagem(ex.getMessage())
        		.status(401).response(ex.toString()).build(), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<RespostaPadrao> handleSQLException(SQLException e) {
        if (e.getSQLState().equals("42S02")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespostaPadrao.builder().mensagem("Table does not exist.").status(404).build());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RespostaPadrao.builder().mensagem("SQL Exception occurred: " + e.getMessage()).status(500).build());
        }
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<RespostaPadrao> handleCustomNotFoundException(CustomNotFoundException e) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespostaPadrao.builder().mensagem(e.getMessage()).status(404).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(RespostaPadrao.builder().status(400).errors(getErrorListFromException(ex)).mensagem("Requisição inválida").build());
    }

    private List<String> getErrorListFromException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.add(fieldError.getDefaultMessage());
        }
        return  errorMessages;
    }

}