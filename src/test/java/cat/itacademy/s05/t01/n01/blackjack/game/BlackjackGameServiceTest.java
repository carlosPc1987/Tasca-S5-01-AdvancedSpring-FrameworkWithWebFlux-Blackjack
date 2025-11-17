package cat.itacademy.s05.t01.n01.blackjack.game;




import cat.itacademy.s05.t01.n01.blackjack.domain.mongo.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.mongo.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.sql.Player;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerMoveRequest;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerMoveType;
import cat.itacademy.s05.t01.n01.blackjack.repository.mongo.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack.repository.sql.PlayerRepository;
import cat.itacademy.s05.t01.n01.blackjack.service.BlackjackGameService;
import cat.itacademy.s05.t01.n01.blackjack.utils.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class BlackjackGameServiceTest {

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private BlackjackGameService gameService;

    @BeforeEach
    void setUp() {
        playerRepository = Mockito.mock(PlayerRepository.class);
        gameRepository = Mockito.mock(GameRepository.class);
        gameService = new BlackjackGameService(playerRepository, gameRepository);
    }

    @Test
    void createGame_shouldReturnNewGame() {
        String playerName = "Luis";
        Player player = new Player(1L, playerName, 0, 0);

        when(playerRepository.findByName(playerName)).thenReturn(Mono.just(player));
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> {
            Game g = invocation.getArgument(0);
            g.setId("game123");
            return Mono.just(g);
        });

        StepVerifier.create(gameService.createGame(playerName))
                .expectNextMatches(game -> game.getId().equals("game123") && game.getPlayerName().equals(playerName))
                .verifyComplete();

        verify(playerRepository, times(1)).findByName(playerName);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void getGame_shouldReturnGame() {
        String gameId = "game123";
        Game game = new Game();
        game.setId(gameId);
        game.setPlayerName("Luis");
        game.setState(GameState.IN_PROGRESS);

        when(gameRepository.findById(gameId)).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.getGame(gameId))
                .expectNextMatches(resp -> resp.getId().equals(gameId) && resp.getPlayerName().equals("Luis"))
                .verifyComplete();

        verify(gameRepository, times(1)).findById(gameId);
    }

    @Test
    void playMove_hit_shouldAddCard() {
        String gameId = "game123";
        Game game = new Game();
        game.setId(gameId);
        game.setPlayerHand(new ArrayList<>());
        game.setDealerHand(new ArrayList<>());
        game.setDeck(new cat.itacademy.s05.t01.n01.blackjack.utils.Deck(new ArrayList<>(List.of(
                new Card("A", "Hearts", 11)
        ))));
        game.setState(GameState.IN_PROGRESS);

        PlayerMoveRequest move = new PlayerMoveRequest(PlayerMoveType.HIT, 10.0);

        when(gameRepository.findById(gameId)).thenReturn(Mono.just(game));
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(gameService.playMove(gameId, move))
                .expectNextMatches(resp -> resp.getPlayerHand().size() == 1 && resp.getState() == GameState.IN_PROGRESS)
                .verifyComplete();

        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void deleteGame_shouldComplete() {
        String gameId = "game123";
        Game game = new Game();
        game.setId(gameId);

        when(gameRepository.findById(gameId)).thenReturn(Mono.just(game));
        when(gameRepository.deleteById(gameId)).thenReturn(Mono.empty());

        StepVerifier.create(gameService.deleteGame(gameId))
                .verifyComplete();

        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, times(1)).deleteById(gameId);
    }

    @Test
    void getGamesByPlayer_shouldReturnFlux() {
        String playerName = "Luis";
        Game game1 = new Game();
        game1.setId("g1");
        game1.setPlayerName(playerName);
        Game game2 = new Game();
        game2.setId("g2");
        game2.setPlayerName(playerName);

        when(gameRepository.findByPlayerName(playerName)).thenReturn(Flux.just(game1, game2));

        StepVerifier.create(gameService.getGamesByPlayer(playerName))
                .expectNextCount(2)
                .verifyComplete();

        verify(gameRepository, times(1)).findByPlayerName(playerName);
    }
}



