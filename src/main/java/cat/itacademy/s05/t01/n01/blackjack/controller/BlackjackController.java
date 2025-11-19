package cat.itacademy.s05.t01.n01.blackjack.controller;

import cat.itacademy.s05.t01.n01.blackjack.dto.request.GameCreationRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.GameActionRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.response.BlackjackGameResponse;
import cat.itacademy.s05.t01.n01.blackjack.service.BlackjackGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/game")
@Validated
@Tag(name = "Game", description = "API para gestionar partidas de Blackjack")
public class BlackjackController {

    private final BlackjackGameService gameService;

    public BlackjackController(BlackjackGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva partida", description = "Crea una nueva partida de Blackjack para un jugador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Partida creada", content = @Content(schema = @Schema(implementation = BlackjackGameResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    public Mono<BlackjackGameResponse> createGame(@Valid @RequestBody GameCreationRequest request) {
        log.info("POST /api/game/new - Creando nueva partida para jugador: {}", request.getPlayerName());
        return gameService.createGame(request.getPlayerName())
                .doOnSuccess(game -> log.debug("Partida creada exitosamente con ID: {}", game.getId()))
                .doOnError(error -> log.error("Error al crear partida: {}", error.getMessage()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener partida", description = "Obtiene los detalles de una partida por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partida encontrada", content = @Content(schema = @Schema(implementation = BlackjackGameResponse.class))),
            @ApiResponse(responseCode = "404", description = "Partida no encontrada", content = @Content)
    })
    public Mono<BlackjackGameResponse> getGame(
            @Parameter(description = "ID de la partida", example = "507f1f77bcf86cd799439011")
            @PathVariable String id) {
        log.info("GET /api/game/{} - Obteniendo detalles de la partida", id);
        return gameService.getGame(id)
                .doOnSuccess(game -> log.debug("Partida encontrada: {}", id))
                .doOnError(error -> log.error("Error al obtener partida {}: {}", id, error.getMessage()));
    }

    @PostMapping("/{id}/play")
    @Operation(summary = "Jugar una partida", description = "Realiza una jugada en una partida existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jugada realizada", content = @Content(schema = @Schema(implementation = BlackjackGameResponse.class))),
            @ApiResponse(responseCode = "400", description = "Jugada inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Partida no encontrada", content = @Content)
    })
    public Mono<BlackjackGameResponse> playGame(
            @Parameter(description = "ID de la partida", example = "507f1f77bcf86cd799439011")
            @PathVariable String id,
            @Valid @RequestBody GameActionRequest request) {
        log.info("POST /api/game/{}/play - Realizando jugada: {}", id, request.getAction());
        return gameService.playMove(id, request)
                .doOnSuccess(game -> log.debug("Jugada realizada exitosamente en partida: {}", id))
                .doOnError(error -> log.error("Error en jugada para partida {}: {}", id, error.getMessage()));
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar partida", description = "Elimina una partida por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Partida eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Partida no encontrada", content = @Content)
    })
    public Mono<Void> deleteGame(
            @Parameter(description = "ID de la partida", example = "507f1f77bcf86cd799439011")
            @PathVariable String id) {
        log.info("DELETE /api/game/{}/delete - Eliminando partida", id);
        return gameService.deleteGame(id)
                .doOnSuccess(unused -> log.debug("Partida eliminada exitosamente: {}", id))
                .doOnError(error -> log.error("Error al eliminar partida {}: {}", id, error.getMessage()));
    }

    @GetMapping("/player/{name}")
    @Operation(summary = "Obtener partidas por jugador", description = "Obtiene todas las partidas de un jugador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de partidas", content = @Content(schema = @Schema(implementation = BlackjackGameResponse.class))),
            @ApiResponse(responseCode = "404", description = "Jugador no encontrado", content = @Content)
    })
    public Flux<BlackjackGameResponse> fetchGamesByPlayer(
            @Parameter(description = "Nombre del jugador", example = "Carlos")
            @PathVariable String name) {
        log.info("GET /api/game/player/{} - Obteniendo partidas del jugador", name);
        return gameService.getGamesByPlayer(name)
                .doOnNext(game -> log.debug("Partida encontrada para jugador {}: {}", name, game.getId()))
                .doOnError(error -> log.error("Error al obtener partidas del jugador {}: {}", name, error.getMessage()));
    }
}