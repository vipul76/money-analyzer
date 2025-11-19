package com.moneyanalyzer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {
    @Bean
    public OpenAPI moneyAnalyzerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Money Analyzer API")
                        .version("1.0")
                        .description("API documentation for the Money Analyzer application"));
    }
}
