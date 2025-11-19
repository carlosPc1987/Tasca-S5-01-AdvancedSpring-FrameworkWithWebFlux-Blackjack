package cat.itacademy.s05.t01.n01.blackjack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Schema(description = "DTO para actualizar el nombre de un jugador")
public class PlayerUpdateRequest {

    @NotBlank(message = "El nombre del jugador es obligatorio")
    @Schema(description = "Nuevo nombre del jugador", example = "Carlos", required = true)
    private String name;
}