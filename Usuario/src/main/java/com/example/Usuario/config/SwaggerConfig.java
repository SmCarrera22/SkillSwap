package com.example.Usuario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API 2026 SkillSwap")
                .version("1.0")
                .description("Documentación de la API de SkillSwap 2026")
                .contact(new Contact()
                    .name("Equipo de Soporte SkillSwap")
                    .email("soporte@skillswap.com")
                    .url("https://skillswap.com/soporte")
                )
            );
    }
}
