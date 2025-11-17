package cat.itacademy.s05.t01.n01.blackjack.game;

import cat.itacademy.s05.t01.n01.blackjack.controller.BlackjackController;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.GameCreationRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerMoveRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.response.BlackjackGameResponse;
import cat.itacademy.s05.t01.n01.blackjack.service.BlackjackGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BlackjackControllerTest {

    private BlackjackGameService gameService;
    private BlackjackController gameController;

    @BeforeEach
    void setUp() {
        gameService = Mockito.mock(BlackjackGameService.class);
        gameController = new BlackjackController(gameService);
    }

    @Test
    void startGame_shouldReturnGameResponse() {
        String playerName = "Luis";
        GameCreationRequest request = new GameCreationRequest();
        request.setPlayerName(playerName);

        BlackjackGameResponse mockResponse = BlackjackGameResponse.builder()
                .id("game1")
                .playerName(playerName)
                .build();

        when(gameService.createGame(playerName)).thenReturn(Mono.just(mockResponse));

        Mono<BlackjackGameResponse> result = gameController.startGame(request);

        StepVerifier.create(result)
                .expectNextMatches(game -> game.getPlayerName().equals(playerName) && game.getId().equals("game1"))
                .verifyComplete();

        verify(gameService, times(1)).createGame(playerName);
    }

    @Test
    void fetchGame_shouldReturnGameResponse() {
        String gameId = "game1";
        BlackjackGameResponse mockResponse = BlackjackGameResponse.builder()
                .id(gameId)
                .playerName("Luis")
                .build();

        when(gameService.getGame(gameId)).thenReturn(Mono.just(mockResponse));

        Mono<BlackjackGameResponse> result = gameController.fetchGame(gameId);

        StepVerifier.create(result)
                .expectNextMatches(game -> game.getId().equals(gameId))
                .verifyComplete();

        verify(gameService, times(1)).getGame(gameId);
    }

    @Test
    void makeMove_shouldReturnGameResponse() {
        String gameId = "game1";
        PlayerMoveRequest move = new PlayerMoveRequest();
        BlackjackGameResponse mockResponse = BlackjackGameResponse.builder()
                .id(gameId)
                .playerName("Luis")
                .build();

        when(gameService.playMove(gameId, move)).thenReturn(Mono.just(mockResponse));

        Mono<BlackjackGameResponse> result = gameController.makeMove(gameId, move);

        StepVerifier.create(result)
                .expectNextMatches(game -> game.getId().equals(gameId))
                .verifyComplete();

        verify(gameService, times(1)).playMove(gameId, move);
    }

    @Test
    void removeGame_shouldReturnVoid() {
        String gameId = "game1";

        when(gameService.deleteGame(gameId)).thenReturn(Mono.empty());

        Mono<Void> result = gameController.removeGame(gameId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(gameService, times(1)).deleteGame(gameId);
    }

    @Test
    void fetchGamesByPlayer_shouldReturnFlux() {
        String playerName = "Luis";
        BlackjackGameResponse mockResponse = BlackjackGameResponse.builder()
                .id("game1")
                .playerName(playerName)
                .build();

        when(gameService.getGamesByPlayer(playerName)).thenReturn(Flux.just(mockResponse));

        Flux<BlackjackGameResponse> result = gameController.fetchGamesByPlayer(playerName);

        StepVerifier.create(result)
                .expectNextMatches(game -> game.getPlayerName().equals(playerName))
                .verifyComplete();

        verify(gameService, times(1)).getGamesByPlayer(playerName);
    }
}


