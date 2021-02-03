import auth.OauthUser;
import auth.OrderServiceBasicAuthenticator;
import auth.OrderServiceOAuthAuthenticator;
import auth.BasicUser;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreakerBundle;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreakerConfiguration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.Application;
import io.dropwizard.auth.*;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import resources.OrderResource;

import java.security.Principal;

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
    }

    public void run(OrderServiceConfiguration configuration, Environment environment) {
        System.out.println(configuration.getApplicationName());
        // Register Application Resources
        registerResources(environment);
        // Setup Basic Authentication
//        registerBasicUserAuth(environment);
        // Setup OAuth2 Authentication
        //registerOAuth2UserAuth(environment);
        // Setup Polymorphic Authentication(Basic and OAuth)
        registerPolymorphicAuth(environment);
    }

    private void registerPolymorphicAuth(Environment environment) {
        final AuthFilter<BasicCredentials, BasicUser> basicFilter
                = new BasicCredentialAuthFilter.Builder<BasicUser>()
                .setAuthenticator(new OrderServiceBasicAuthenticator())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter();
        final AuthFilter<String, OauthUser> oauthFilter
                = new OAuthCredentialAuthFilter.Builder<OauthUser>()
                .setAuthenticator(new OrderServiceOAuthAuthenticator())
                .setPrefix("Bearer")
                .buildAuthFilter();
        final PolymorphicAuthDynamicFeature<Principal> feature = new PolymorphicAuthDynamicFeature<>(
                ImmutableMap.of(
                        BasicUser.class, basicFilter,
                        OauthUser.class, oauthFilter));
        final AbstractBinder binder = new PolymorphicAuthValueFactoryProvider.Binder<>(
                ImmutableSet.of(BasicUser.class, OauthUser.class));
        environment.jersey().register(feature);
        environment.jersey().register(binder);
    }

    private void registerOAuth2UserAuth(Environment environment) {
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<OauthUser>()
                .setAuthenticator(new OrderServiceOAuthAuthenticator())
                .setPrefix("Bearer")
                .buildAuthFilter()
        ));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(BasicUser.class));
    }

    private void registerResources(Environment environment) {
        environment.jersey().register(new OrderResource());
    }

    private void registerBasicUserAuth(Environment environment) {
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<BasicUser>()
                .setAuthenticator(new OrderServiceBasicAuthenticator())
                .buildAuthFilter()
        ));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(BasicUser.class));
    }

    public static void main(String[] args) throws Exception {
        new OrderServiceApplication().run(args);
    }
}
