package cat.itacademy.s05.t01.n01.blackjack.dto.response;

import cat.itacademy.s05.t01.n01.blackjack.domain.mongo.Card;
import cat.itacademy.s05.t01.n01.blackjack.utils.GameState;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlackjackGameResponse {
    private String id;
    private String playerName;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private GameState state;
}