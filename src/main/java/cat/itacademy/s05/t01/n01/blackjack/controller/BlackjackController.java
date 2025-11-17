package cat.itacademy.s05.t01.n01.blackjack.controller;

import cat.itacademy.s05.t01.n01.blackjack.dto.request.GameCreationRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerMoveRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.response.BlackjackGameResponse;
import cat.itacademy.s05.t01.n01.blackjack.service.BlackjackGameService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/game")
public class BlackjackController {

    private final BlackjackGameService gameService;

    public BlackjackController(BlackjackGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public Mono<BlackjackGameResponse> startGame(@RequestBody GameCreationRequest request) {
        return gameService.createGame(request.getPlayerName());
    }

    @GetMapping("/{id}")
    public Mono<BlackjackGameResponse> fetchGame(@PathVariable String id) {
        return gameService.getGame(id);
    }

    @PostMapping("/{id}/move")
    public Mono<BlackjackGameResponse> makeMove(@PathVariable String id, @RequestBody PlayerMoveRequest move) {
        return gameService.playMove(id, move);
    }

    @DeleteMapping("/{id}/remove")
    public Mono<Void> removeGame(@PathVariable String id) {
        return gameService.deleteGame(id);
    }

    @GetMapping("/player/{name}")
    public Flux<BlackjackGameResponse> fetchGamesByPlayer(@PathVariable String name) {
        return gameService.getGamesByPlayer(name);
    }
}
