package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2 )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.ecommerce.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("ecommerce API Documentation")
                        .description("Comprehensive documentation of all endpoints in the ecommerce project")
                        .version("1.0")
                        .build());
    }
}