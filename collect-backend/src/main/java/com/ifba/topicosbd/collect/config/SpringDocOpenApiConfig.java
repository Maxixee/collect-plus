package com.ifba.topicosbd.collect.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(
                new Info()
                        .title("REST API - Collect Plus")
                        .description("Esta API foi desenvolvida para gerenciar o sistema de coleta de resíduos sólidos de uma cidade.")
                        .version("v1")
        );
    }

}
