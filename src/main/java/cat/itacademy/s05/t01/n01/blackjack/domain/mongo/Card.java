package cat.itacademy.s05.t01.n01.blackjack.domain.mongo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    private String suit;
    private String rank;
    private int value;
}