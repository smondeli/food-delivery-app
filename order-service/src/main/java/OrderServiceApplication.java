import auth.OrderServiceAuthenticator;
import auth.User;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreakerBundle;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreakerConfiguration;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resources.OrderResource;

public class OrderServiceApplication extends Application<OrderServiceConfiguration> {
    private final CircuitBreakerBundle<OrderServiceConfiguration> circuitBreakerBundle = new CircuitBreakerBundle<OrderServiceConfiguration>() {
        @Override
        protected CircuitBreakerConfiguration getConfiguration(final OrderServiceConfiguration configuration) {
            return configuration.getCircuitBreaker();
        }
    };

    @Override
    public void initialize(final Bootstrap<OrderServiceConfiguration> bootstrap) {
        bootstrap.addBundle(this.circuitBreakerBundle);
    };

    public void run(OrderServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
        // Register Application Resources
        registerResources(environment);
        // Setup Basic Authentication
        registerBasicUserAuth(environment);
    }

    private void registerResources(Environment environment) {
        environment.jersey().register(new OrderResource());
    }

    private void registerBasicUserAuth(Environment environment) {
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new OrderServiceAuthenticator())
                .buildAuthFilter()
        ));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    public static void main(String[] args) throws Exception {
        new OrderServiceApplication().run(args);
    }
}
