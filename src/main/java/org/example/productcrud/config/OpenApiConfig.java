package org.example.productcrud.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product CRUD API",
                version = "1.0",
                description = "A REST API for managing product inventory with full CRUD operations, built using Spring Boot, Spring Data JPA, and MySQL."
        )
)
public class OpenApiConfig {
}
