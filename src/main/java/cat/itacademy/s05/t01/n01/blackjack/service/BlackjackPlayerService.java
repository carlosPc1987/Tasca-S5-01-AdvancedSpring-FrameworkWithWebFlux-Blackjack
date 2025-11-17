package cat.itacademy.s05.t01.n01.blackjack.service;

import cat.itacademy.s05.t01.n01.blackjack.domain.sql.Player;
import cat.itacademy.s05.t01.n01.blackjack.dto.response.PlayerRankingResponse;
import cat.itacademy.s05.t01.n01.blackjack.exception.*;
import cat.itacademy.s05.t01.n01.blackjack.repository.sql.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class BlackjackPlayerService {

    private final PlayerRepository playerRepository;

    public BlackjackPlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> addPlayer(String name) {
        if (name == null || name.isBlank()) {
            return Mono.error(new AttributeInvalidException("El nombre del jugador no puede estar vacío"));
        }

        return playerRepository.findByName(name)
                .flatMap(p -> Mono.error(new DuplicatePlayerException("Ya existe un jugador con el nombre: " + name)))
                .switchIfEmpty(Mono.defer(() ->
                        playerRepository.save(Player.builder()
                                .name(name.trim())
                                .gamesPlayed(0)
                                .gamesWon(0)
                                .build()
                        )
                ))
                .cast(Player.class);
    }

    public Mono<Player> updatePlayerName(Long playerId, String newName) {
        if (newName == null || newName.isBlank()) {
            return Mono.error(new AttributeInvalidException("El nombre del jugador no puede estar vacío"));
        }

        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Jugador no encontrado")))
                .flatMap(player -> {
                    player.setName(newName.trim());
                    return playerRepository.save(player);
                });
    }

    public Mono<Player> getPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Jugador no encontrado")));
    }

    public Mono<Void> removePlayer(Long playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Jugador no encontrado")))
                .flatMap(existing -> playerRepository.deleteById(playerId));
    }

    public Flux<PlayerRankingResponse> fetchRanking() {
        return playerRepository.findAllByOrderByGamesWonDesc()
                .map(player -> PlayerRankingResponse.builder()
                        .playerId(player.getId())
                        .playerName(player.getName())
                        .winRate(player.getGamesPlayed() == 0 ? 0.0 :
                                (double) player.getGamesWon() / player.getGamesPlayed())
                        .totalGames(player.getGamesPlayed())
                        .build());
    }
}
