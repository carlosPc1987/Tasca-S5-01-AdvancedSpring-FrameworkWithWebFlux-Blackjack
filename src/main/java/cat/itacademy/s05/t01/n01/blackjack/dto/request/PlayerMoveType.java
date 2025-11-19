package cat.itacademy.s05.t01.n01.blackjack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo de jugada disponible en el Blackjack")
public enum PlayerMoveType {
    @Schema(description = "Pedir una carta")
    HIT,

    @Schema(description = "Plantarse")
    STAND,

    @Schema(description = "Doblar la apuesta y recibir una última carta")
    DOUBLE
}