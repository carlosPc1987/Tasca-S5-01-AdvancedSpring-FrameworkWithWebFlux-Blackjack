package cat.itacademy.s05.t01.n01.blackjack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "DTO para crear una nueva partida de Blackjack")
public class GameCreationRequest {

    @NotBlank(message = "El nombre del jugador es obligatorio")
    @Schema(description = "Nombre del jugador", example = "Carlos", required = true)
    private String playerName;
}