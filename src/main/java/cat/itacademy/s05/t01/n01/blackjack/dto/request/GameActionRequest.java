package cat.itacademy.s05.t01.n01.blackjack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Solicitud para realizar una acción en el juego de Blackjack")
public class GameActionRequest {

    @NotNull(message = "El tipo de acción es obligatorio")
    @Schema(description = "Tipo de acción a realizar en el juego", example = "HIT", required = true)
    private PlayerMoveType action;

    @Schema(description = "Cantidad apostada", example = "50.0")
    private Double bet;
}