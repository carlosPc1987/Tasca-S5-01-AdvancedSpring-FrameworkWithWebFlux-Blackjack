package cat.itacademy.s05.t01.n01.blackjack.domain.mongo;

import cat.itacademy.s05.t01.n01.blackjack.dto.request.GameActionRequest;
import cat.itacademy.s05.t01.n01.blackjack.utils.Deck;
import cat.itacademy.s05.t01.n01.blackjack.utils.GameState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.*;


@Document(collection = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    private String id;

    private String playerId;
    private String playerName;
    private GameState state;
    private Deck deck;
    @Builder.Default
    private List<Card> playerHand = new ArrayList<>();
    @Builder.Default
    private List<Card> dealerHand = new ArrayList<>();
    @Builder.Default
    private List<GameActionRequest> moves = new ArrayList<>();

    private Instant createdAt;
    private Instant updatedAt;

}

