package com.juliano.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Value("${config-routes.excluded-routes}")
    private ArrayList<String> excludedRoutes;

    @Autowired
    private AccountsInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludedRoutes);
    }
}
