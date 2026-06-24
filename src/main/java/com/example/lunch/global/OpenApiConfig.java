package com.example.lunch.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI lunchOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lunch Menu API")
                        .description("학교 급식 메뉴 등록 및 조회 REST API")
                        .version("v1.0.0"));
    }
}
