package stackover.gateway.service.util;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Claims;
import stackover.gateway.service.exception.ValidateRequestException;


@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter {

    @Value("${JWT_SECRET}")
    private String SECRET_KEY;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = extractToken(exchange);

        if (token == null || !token.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String jwtToken = token.substring(7);

        return Mono.just(jwtToken)
                .flatMap(validToken -> {
                    try {
                        Claims claims = validateToken(validToken);
                        exchange.getAttributes().put("claims", claims);
                        return chain.filter(exchange);
                    } catch (ValidateRequestException e) {
                        return Mono.error(new ValidateRequestException("Ошибка при валидации токена", e));
                    }
                })
                .onErrorResume(e -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }
    private Claims validateToken(String token) throws ValidateRequestException {
        log.info("Валидация токена: {}", token);
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
    }

    private String extractToken(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("Authorization");
    }

}