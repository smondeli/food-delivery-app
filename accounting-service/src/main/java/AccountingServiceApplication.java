import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreakerBundle;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreakerConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resources.AccountingResource;

public class AccountingServiceApplication extends Application<AccountingServiceConfiguration> {
    private final CircuitBreakerBundle<AccountingServiceConfiguration> circuitBreakerBundle = new CircuitBreakerBundle<AccountingServiceConfiguration>() {
        @Override
        protected CircuitBreakerConfiguration getConfiguration(final AccountingServiceConfiguration configuration) {
            return configuration.getCircuitBreaker();
        }
    };

    @Override
    public void initialize(final Bootstrap<AccountingServiceConfiguration> bootstrap) {
        bootstrap.addBundle(this.circuitBreakerBundle);
    };
    public void run(AccountingServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
        environment.jersey().register(new AccountingResource());
    }

    public static void main(String[] args) throws Exception {
        new AccountingServiceApplication().run(args);
    }
}
