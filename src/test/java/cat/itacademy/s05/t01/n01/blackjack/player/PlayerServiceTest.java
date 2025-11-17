package cat.itacademy.s05.t01.n01.blackjack.player;

import cat.itacademy.s05.t01.n01.blackjack.domain.sql.Player;
import cat.itacademy.s05.t01.n01.blackjack.exception.DuplicatePlayerException;
import cat.itacademy.s05.t01.n01.blackjack.exception.EntityNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack.repository.sql.PlayerRepository;
import cat.itacademy.s05.t01.n01.blackjack.service.BlackjackPlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class PlayerServiceTest {

    private PlayerRepository playerRepository;
    private BlackjackPlayerService playerService;

    @BeforeEach
    void setUp() {
        playerRepository = mock(PlayerRepository.class);
        playerService = new BlackjackPlayerService(playerRepository);
    }

    @Test
    void addPlayer_duplicateName() {
        String name = "Luis";

        when(playerRepository.findByName(name)).thenReturn(Mono.just(new Player()));

        StepVerifier.create(playerService.addPlayer(name))
                .expectError(DuplicatePlayerException.class) // usar DuplicatePlayerException
                .verify();

        verify(playerRepository, never()).save(any());
    }

    @Test
    void addPlayer_newPlayer() {
        String name = "Ana";

        when(playerRepository.findByName(name)).thenReturn(Mono.empty());
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(playerService.addPlayer(name))
                .expectNextMatches(player -> player.getName().equals(name))
                .verifyComplete();

        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void getPlayerById_existing() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Luis");

        when(playerRepository.findById(1L)).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.getPlayerById(1L))
                .expectNextMatches(p -> p.getId().equals(1L) && p.getName().equals("Luis"))
                .verifyComplete();
    }

    @Test
    void getPlayerById_notFound() {
        when(playerRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(playerService.getPlayerById(1L))
                .expectError(EntityNotFoundException.class)
                .verify();
    }

    @Test
    void updatePlayerName_existing() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Luis");

        when(playerRepository.findById(1L)).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(playerService.updatePlayerName(1L, "Carlos"))
                .expectNextMatches(p -> p.getName().equals("Carlos"))
                .verifyComplete();

        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void updatePlayerName_notFound() {
        when(playerRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(playerService.updatePlayerName(1L, "Carlos"))
                .expectError(EntityNotFoundException.class)
                .verify();
    }

    @Test
    void removePlayer_existing() {
        Player player = new Player();
        player.setId(1L);

        when(playerRepository.findById(1L)).thenReturn(Mono.just(player));
        when(playerRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(playerService.removePlayer(1L))
                .verifyComplete();

        verify(playerRepository, times(1)).deleteById(1L);
    }

    @Test
    void removePlayer_notFound() {
        when(playerRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(playerService.removePlayer(1L))
                .expectError(EntityNotFoundException.class)
                .verify();

        verify(playerRepository, never()).deleteById((Long) any());
    }
}


