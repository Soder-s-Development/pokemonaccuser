package com.juliano.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Value("${config-routes.excluded-routes}")
    private ArrayList<String> excludedRoutes;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AccountsInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludedRoutes);
    }
}
