package cat.itacademy.s05.t01.n01.blackjack.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerCreationRequest {
    private String name;
}