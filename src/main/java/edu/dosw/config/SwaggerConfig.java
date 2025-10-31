package edu.dosw.config;

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
        .info(
            new Info()
                .title("MasterChef API")
                .version("1.0")
                .description("API para gestión de recetas de MasterChef")
                .contact(new Contact().name("Equipo MasterChef").email("support@masterchef.com")));
  }
}
