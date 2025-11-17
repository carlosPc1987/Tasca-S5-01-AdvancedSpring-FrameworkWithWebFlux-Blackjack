package cat.itacademy.s05.t01.n01.blackjack.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI  blackjackApiDocumentation() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Blackjack")
                        .description("API REST para manejar jugadores y partidas del juego de Blackjack")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Carlos de Cózar Ruiz-Salinas")
                                .email("cadecozarrus@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio del proyecto en GitHub")
                        .url("https://github.com/tuUsuario/miBlackjack"));
    }
}
