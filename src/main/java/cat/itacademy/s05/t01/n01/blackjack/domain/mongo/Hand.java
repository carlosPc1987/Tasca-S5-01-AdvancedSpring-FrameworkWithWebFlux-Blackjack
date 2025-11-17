package cat.itacademy.s05.t01.n01.blackjack.domain.mongo;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hand {
    private List<Card> cards;
    private int totalValue;
    private boolean isDealer;
    private boolean isStand;
}