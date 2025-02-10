package stackover.gateway.service.enums;

public enum ServiceLocation {
    STACKOVER_AUTH_SERVICE("lb://STACKOVER-AUTH-SERVICE"),
    STACKOVER_PROFILE_SERVICE("lb://STACKOVER-PROFILE-SERVICE"),
    STACKOVER_RESOURCE_SERVICE("lb://STACKOVER-RESOURCE-SERVICE"),
    STACKOVER_EUREKA_SERVICE("lb://STACKOVER-EUREKA-SERVICE");

    private final String uri;

    ServiceLocation(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
