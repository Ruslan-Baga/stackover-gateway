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
                .route("auth-refresh", r -> r.path("/api/auth/refresh")
                        .filters(a -> a.addRequestHeader("From-Gateway", "true"))
                        .uri(ServiceLocation.STACKOVER_AUTH_SERVICE.getUri())
                )
                .route("auth-mapping",
                        route -> route
                                .path("/api/auth/**")
                                .uri(ServiceLocation.STACKOVER_AUTH_SERVICE.getUri())

                )
                .route("auth-service", r -> r.path("/api/internal/account/**")
                        .filters(f -> f.filter(authenticationFilter).addRequestHeader("From-Gateway", "true"))
                        .uri(ServiceLocation.STACKOVER_AUTH_SERVICE.getUri())
                )
                .route("profile-service", r -> r.path("/api/profile/**", "/api/inner/profile/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(ServiceLocation.STACKOVER_PROFILE_SERVICE.getUri())
                )
                .route("resource-service", r -> r.path("/api/user/question/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(ServiceLocation.STACKOVER_RESOURCE_SERVICE.getUri())
                )
                .route("eureka-server", r -> r.path("/eureka/**")
                        .uri(ServiceLocation.STACKOVER_EUREKA_SERVICE.getUri())
                )

                .build();
    }

}
