package cat.itacademy.s05.t01.n01.blackjack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    @GetMapping("/")
    public Mono<String> home() {
        return Mono.just("redirect:/swagger-ui.html");
    }

    @GetMapping("/api")
    public Mono<String> apiHome() {
        return Mono.just("redirect:/swagger-ui.html");
    }
}