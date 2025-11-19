package cat.itacademy.s05.t01.n01.blackjack.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI blackjackApiDocumentation() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Servidor Local - Docker"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local Directo")
                ))
                .info(new Info()
                        .title("API Blackjack - WebFlux")
                        .description("API REST Reactiva para manejar jugadores y partidas del juego de Blackjack")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Carlos de Cózar Ruiz-Salinas")
                                .email("cadecozarrus@gmail.com")
                                .url("https://github.com/carlosPc1987/"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación del Proyecto")
                        .url("https://github.com/carlosPc1987/Tasca-S5-01-AdvancedSpring-FrameworkWithWebFlux-Blackjack"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("blackjack-public")
                .packagesToScan("cat.itacademy.s05.t01.n01.blackjack.controller")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi gameApi() {
        return GroupedOpenApi.builder()
                .group("game")
                .displayName("Gestión de Partidas")
                .pathsToMatch("/api/game/**")
                .build();
    }

    @Bean
    public GroupedOpenApi playerApi() {
        return GroupedOpenApi.builder()
                .group("player")
                .displayName("Gestión de Jugadores")
                .pathsToMatch("/api/player/**")
                .build();
    }

    @Bean
    public GroupedOpenApi blackjackApi() {
        return GroupedOpenApi.builder()
                .group("blackjack")
                .displayName("Blackjack Completo")
                .pathsToMatch("/api/**")
                .build();
    }
}