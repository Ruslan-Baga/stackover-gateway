package stackover.gateway.service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stackover.gateway.service.enums.ServiceLocation;

@Configuration
public class RouterConfig {

    @Bean
    public RouteLocator commonRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-mapping",
                        route -> route
                                .path("/api/auth/**")
                                .uri(ServiceLocation.STACKOVER_AUTH_SERVICE.getUri())
                )

                .build();
    }

}
