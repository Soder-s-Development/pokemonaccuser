package com.juliano.app.config;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class Midleware {
	private final static String token = "cG9rZW1vbldvcmxkSnVsaWFub1NvZGVy";

    public boolean tokenRequest(ServletRequest servletRequest){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String authorization = req.getHeader("Authorization");
        if(authorization == null || authorization.isBlank()) {
        	return false;
        }
        return validateToken(authorization);
    }
    
    public boolean validateToken(String bearer){
    	//aqui cabe uma validacao detalhhada
    	return bearer.split("Bearer ").length > 1 && token.equals(bearer.split("Bearer ")[1]);
    }
    
    public String getToken(){
        return token;
    }
    
    public ResponseEntity tokenOk(String bearer){
        if(!validateToken(bearer)) {
        	return ResponseEntity.status(401).body("Token inválido");
        }
		return null;
    }
}
