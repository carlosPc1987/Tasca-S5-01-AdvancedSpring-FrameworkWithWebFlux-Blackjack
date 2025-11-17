package cat.itacademy.s05.t01.n01.blackjack.controller;

import cat.itacademy.s05.t01.n01.blackjack.domain.sql.Player;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerCreationRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerUpdateRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.response.PlayerRankingResponse;
import cat.itacademy.s05.t01.n01.blackjack.service.BlackjackPlayerService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/player")
public class BlackjackPlayerController {

    private final BlackjackPlayerService playerService;

    public BlackjackPlayerController(BlackjackPlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/create")
    public Mono<Player> createPlayer(@RequestBody PlayerCreationRequest request) {
        return playerService.addPlayer(request.getName());
    }

    @PutMapping("/{id}/update")
    public Mono<Player> changePlayerName(@PathVariable Long id, @RequestBody PlayerUpdateRequest request) {
        return playerService.updatePlayerName(id, request.getName());
    }

    @GetMapping("/{id}")
    public Mono<Player> fetchPlayer(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @DeleteMapping("/{id}/remove")
    public Mono<Void> removePlayer(@PathVariable Long id) {
        return playerService.removePlayer(id);
    }

    @GetMapping("/leaderboard")
    public Flux<PlayerRankingResponse> fetchRanking() {
        return playerService.fetchRanking();
    }
}

