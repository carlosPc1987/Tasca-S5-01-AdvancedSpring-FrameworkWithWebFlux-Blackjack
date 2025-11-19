package cat.itacademy.s05.t01.n01.blackjack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear un nuevo jugador")
public class PlayerCreationRequest {

    @NotBlank(message = "El nombre del jugador es obligatorio")
    @Schema(description = "Nombre del jugador", example = "Carlos", required = true)
    private String name;
}