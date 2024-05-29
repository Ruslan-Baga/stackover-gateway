package stackover.gateway.service.enums;

public enum ServiceLocation {
    STACKOVER_AUTH_SERVICE("lb://STACKOVER-AUTH-SERVICE");

    private final String uri;

    ServiceLocation(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
