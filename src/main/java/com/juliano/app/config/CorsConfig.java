package com.juliano.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Configure allowed origins, methods, and headers
        config.addAllowedOrigin("*"); // You can replace "*" with specific origins
        config.addAllowedMethod("*"); // You can restrict to specific HTTP methods (e.g., GET, POST)
        config.addAllowedHeader("*"); // You can restrict to specific headers

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}