import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import resources.RestaurantResource;

public class RestaurantServiceApplication extends Application<RestaurantServiceConfiguration> {
    public void run(RestaurantServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
        environment.jersey().register(new RestaurantResource());
    }

    public static void main(String[] args) throws Exception {
        new RestaurantServiceApplication().run(args);
    }
}
