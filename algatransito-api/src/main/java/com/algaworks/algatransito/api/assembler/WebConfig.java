package com.algaworks.algatransito.api.assembler;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("proprietarios")
                .allowedOrigins("http://127.0.0.1:5500");
    }

}
