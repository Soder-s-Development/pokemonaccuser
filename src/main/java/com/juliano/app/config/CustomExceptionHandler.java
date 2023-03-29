package com.juliano.app.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        
        return new ResponseEntity<>(RespostaPadrao.builder()
        		.mensagem("handleMethodArgumentNotValid")
        		.status(Integer.parseInt(status.toString())).response(errors).build(), status);
    }
    
    @ExceptionHandler({CustomNotAllowedException.class})
    protected ResponseEntity<Object> customNotAllowedException 
    (RuntimeException ex){
    	
    	System.out.println("[ControllerAdvice]: Called customNotAllowedException");
    	
    	return new ResponseEntity<>(RespostaPadrao.builder()
        		.mensagem(ex.getMessage())
        		.status(401).response(ex.toString()).build(), HttpStatus.UNAUTHORIZED);

    	
    }
    
    
}