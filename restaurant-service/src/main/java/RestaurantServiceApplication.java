import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class RestaurantServiceApplication extends Application<RestaurantServiceConfiguration> {
    public void run(RestaurantServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
    }

    public static void main(String[] args) throws Exception {
        new RestaurantServiceApplication().run(args);
    }
}
