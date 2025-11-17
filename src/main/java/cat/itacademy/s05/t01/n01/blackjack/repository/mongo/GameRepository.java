package cat.itacademy.s05.t01.n01.blackjack.repository.mongo;

import cat.itacademy.s05.t01.n01.blackjack.domain.mongo.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    Flux<Game> findByPlayerName(String playerName);
}
