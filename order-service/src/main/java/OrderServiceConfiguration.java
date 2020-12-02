import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreakerConfiguration;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class OrderServiceConfiguration extends Configuration {
    @NotNull
    private String applicationName;
    @NotNull
    private CircuitBreakerConfiguration circuitBreaker;

    public CircuitBreakerConfiguration getCircuitBreaker() {
        return circuitBreaker;
    }

    public void setCircuitBreaker(CircuitBreakerConfiguration circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    @JsonProperty
    public String getApplicationName() {
        return applicationName;
    }

    @JsonProperty
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
