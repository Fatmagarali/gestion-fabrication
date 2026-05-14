package com.fabrication.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Swagger / OpenAPI pour la documentation de l'API.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestion des Ordres de Fabrication")
                        .version("1.0.0")
                        .description("API REST pour la gestion des ordres de fabrication, "
                                + "des produits, des machines et des employés.")
                        .contact(new Contact()
                                .name("Equipe Fabrication")
                                .email("contact@fabrication.com")));
    }
}
