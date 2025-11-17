package cat.itacademy.s05.t01.n01.blackjack.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerMoveRequest {
    private PlayerMoveType moveType;
    private Double bet;
}