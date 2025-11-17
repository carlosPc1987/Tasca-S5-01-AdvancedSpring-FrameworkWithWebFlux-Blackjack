package cat.itacademy.s05.t01.n01.blackjack.domain.sql;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("player")
public class Player {

    @Id
    private Long id;

    private String name;

    @Column("games_played")
    private int gamesPlayed;

    @Column("games_won")
    private int gamesWon;
}
