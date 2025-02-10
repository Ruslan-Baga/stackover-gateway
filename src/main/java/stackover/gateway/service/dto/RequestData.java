package stackover.gateway.service.dto;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные запроса для аутентификации")
public record RequestData(
        @Schema(description = "Токен аутентификации")
        String token,
        @Schema(description = "Данные о пользователе")
        Claims claims
) {
}
