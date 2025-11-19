package cat.itacademy.s05.t01.n01.blackjack.controller;


import cat.itacademy.s05.t01.n01.blackjack.domain.sql.Player;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerCreationRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerUpdateRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.response.PlayerRankingResponse;
import cat.itacademy.s05.t01.n01.blackjack.service.BlackjackPlayerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/player")
public class BlackjackPlayerController {

    private final BlackjackPlayerService playerService;

    public BlackjackPlayerController(BlackjackPlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Player> createPlayer(@Valid @RequestBody PlayerCreationRequest request) {
        log.info("POST /api/player/create - Creando jugador con nombre: {}", request.getName());
        return playerService.addPlayer(request.getName())
                .doOnSuccess(player -> log.debug("Jugador creado exitosamente: {}", player.getId()))
                .doOnError(error -> log.error("Error al crear jugador: {}", error.getMessage()));
    }

    @PutMapping("/{id}/update")
    public Mono<Player> changePlayerName(@PathVariable Long id, @Valid @RequestBody PlayerUpdateRequest request) {
        log.info("PUT /api/player/{}/update - Actualizando nombre a: {}", id, request.getName());
        return playerService.updatePlayerName(id, request.getName())
                .doOnSuccess(player -> log.debug("Nombre del jugador actualizado exitosamente: {}", id))
                .doOnError(error -> log.error("Error al actualizar jugador {}: {}", id, error.getMessage()));
    }

    @GetMapping("/{id}")
    public Mono<Player> fetchPlayer(@PathVariable Long id) {
        log.info("GET /api/player/{} - Obteniendo jugador", id);
        return playerService.getPlayerById(id)
                .doOnSuccess(player -> log.debug("Jugador encontrado: {}", id))
                .doOnError(error -> log.error("Error al obtener jugador {}: {}", id, error.getMessage()));
    }

    @DeleteMapping("/{id}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> removePlayer(@PathVariable Long id) {
        log.info("DELETE /api/player/{}/remove - Eliminando jugador", id);
        return playerService.removePlayer(id)
                .doOnSuccess(unused -> log.debug("Jugador eliminado exitosamente: {}", id))
                .doOnError(error -> log.error("Error al eliminar jugador {}: {}", id, error.getMessage()));
    }

    @GetMapping("/leaderboard")
    public Flux<PlayerRankingResponse> fetchRanking() {
        log.info("GET /api/player/leaderboard - Obteniendo ranking de jugadores");
        return playerService.fetchRanking()
                .doOnNext(player -> log.debug("Jugador en ranking: {} - Win Rate: {}%",
                        player.getPlayerName(), player.getWinRate()))
                .doOnError(error -> log.error("Error al obtener ranking: {}", error.getMessage()));
    }
}