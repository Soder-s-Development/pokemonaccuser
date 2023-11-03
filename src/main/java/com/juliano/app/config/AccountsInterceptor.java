package com.juliano.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

import static com.juliano.app.config.Utils.isNull;

@Component
public class AccountsInterceptor implements HandlerInterceptor {
    @Autowired
    Midleware midleware;

    private String[] whitelist = {"/swagger", "/v2/api-docs", "/error", "/csrf"};

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception
    {
        if(isSwaggerRequest(requestServlet)){
            return true;
        }

        System.out.println("LOG: INTERCEPTOR PREHANDLE CALLED WHIT REQUEST "+requestServlet.getRequestURI());

        Midleware.IncomigJWTObject jwtObject = midleware.getTokenEValidate(requestServlet);
        if(isNull(jwtObject)){
            System.out.println("[INTERCEPTOR]: Not passed");
            throw new CustomNotAllowedException("Token not valid");
        }
        System.out.println("[INTERCEPTOR]: Continue");
        return true;
    }
    @Override
    public void postHandle(
       HttpServletRequest request, HttpServletResponse response, Object handler, 
       ModelAndView modelAndView) throws Exception {
    	System.out.println("[INTERCEPTOR]: Running in posthandle");
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
       Object handler, Exception exception) throws Exception {
    	System.out.println("[INTERCEPTOR]: Running in after completion");
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Arrays.asList(whitelist).contains(requestURI) || requestURI.equals("/");
    }

}
