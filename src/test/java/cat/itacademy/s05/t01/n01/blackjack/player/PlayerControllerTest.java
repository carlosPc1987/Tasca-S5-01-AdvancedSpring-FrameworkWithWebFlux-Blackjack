package cat.itacademy.s05.t01.n01.blackjack.player;

import cat.itacademy.s05.t01.n01.blackjack.controller.BlackjackPlayerController;
import cat.itacademy.s05.t01.n01.blackjack.domain.sql.Player;
import cat.itacademy.s05.t01.n01.blackjack.dto.request.PlayerCreationRequest;
import cat.itacademy.s05.t01.n01.blackjack.service.BlackjackPlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class PlayerControllerTest {

    private BlackjackPlayerService playerService;
    private BlackjackPlayerController playerController;

    @BeforeEach
    void setUp() {
        playerService = Mockito.mock(BlackjackPlayerService.class);
        playerController = new BlackjackPlayerController(playerService);
    }

    @Test
    void createPlayer_shouldCallServiceAndReturnPlayer() {
        String name = "Luis";
        Player mockPlayer = new Player(1L, name, 0, 0);
        PlayerCreationRequest createPlayerRequest = new PlayerCreationRequest();
        createPlayerRequest.setName(name);

        when(playerService.addPlayer(name)).thenReturn(Mono.just(mockPlayer));

        Mono<Player> result = playerController.createPlayer(createPlayerRequest);

        StepVerifier.create(result)
                .expectNextMatches(player -> player.getName().equals(name)
                        && player.getGamesPlayed() == 0
                        && player.getGamesWon() == 0)
                .verifyComplete();

        verify(playerService, times(1)).addPlayer(name);
    }

}
