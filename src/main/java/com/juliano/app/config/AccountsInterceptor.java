package com.juliano.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.juliano.app.config.Utils.isNull;

public class AccountsInterceptor implements HandlerInterceptor {
    // can make a post handler as well

    @Autowired
    Midleware midleware;

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception
    {
        System.out.println("LOG: INTERCEPTOR PREHANDLE CALLED");
        Midleware.IncomigJWTObject jwtObject = midleware.getTokenEValidate(requestServlet);
        if(isNull(jwtObject)){
            // throw exception and catch it with a controller advice todo
            System.out.println("[INTERCEPTOR]: Not passed");
            return false;
        }
        System.out.println("[INTERCEPTOR]: Continue");
        return true;
    }
}
