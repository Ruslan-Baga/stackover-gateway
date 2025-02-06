package stackover.gateway.service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stackover.gateway.service.enums.ServiceLocation;
import stackover.gateway.service.util.AuthenticationFilter;

@Configuration
public class RouterConfig {

    @Bean
    public RouteLocator commonRouteLocator(RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter) {
        return builder.routes()
                .route("auth-mapping",
                        route -> route
                                .path("/api/auth/**")
                                .uri(ServiceLocation.STACKOVER_AUTH_SERVICE.getUri())

                )
                .route("auth-service", r -> r.path("/api/internal/account/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(ServiceLocation.STACKOVER_AUTH_SERVICE.getUri())
                )

                .build();
    }

}
