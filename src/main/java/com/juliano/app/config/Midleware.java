package com.juliano.app.config;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Service
public class Midleware {
    private final static String token = "pokemonWorldJulianoSoder";

    public boolean tokenRequest(ServletRequest servletRequest){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String authorization = req.getHeader("Authorization");
        String auth = null;
        if (authorization.split("").length > 7){
            auth = authorization.split("bearer ")[1];
        }
        boolean equals = auth.equals(token);
        return equals;
    }
    public String getToken(){
        return token;
    }
}
