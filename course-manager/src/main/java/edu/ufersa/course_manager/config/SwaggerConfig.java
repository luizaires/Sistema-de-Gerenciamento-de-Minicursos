package edu.ufersa.course_manager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API de Sistema de Gerenciamento de Minicurso")
                        .version("1.0")
                        .description("Documentação dos endpoints da API para gerenciamento das classes de Usuário, Minicurso e inscrição"));
    }

}
