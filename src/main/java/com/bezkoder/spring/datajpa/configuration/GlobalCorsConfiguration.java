package com.bezkoder.spring.datajpa.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class GlobalCorsConfiguration implements WebMvcConfigurer {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
//                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS","HEAD")
                        .allowedHeaders("*")
                        .allowedOriginPatterns("*");
            }

    }

