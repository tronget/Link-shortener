package com.tronget.linkshortener.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableConfigurationProperties(AppConfig.class)
public class WebConfig implements WebMvcConfigurer {

    private final AppConfig appConfig;

    @Autowired
    public WebConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(appConfig.getClientUrl())
                .allowedHeaders("GET", "POST")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
