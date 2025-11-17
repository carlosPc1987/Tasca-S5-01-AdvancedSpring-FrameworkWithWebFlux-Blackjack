package cat.itacademy.s05.t01.n01.blackjack.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerRankingResponse {
    private Long playerId;
    private String playerName;
    private Double winRate;
    private Integer totalGames;
}