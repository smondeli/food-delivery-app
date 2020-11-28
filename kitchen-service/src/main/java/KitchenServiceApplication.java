import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import resources.KitchenResource;

public class KitchenServiceApplication extends Application<KitchenServiceConfiguration> {
    public void run(KitchenServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
        environment.jersey().register(new KitchenResource());
    }

    public static void main(String[] args) throws Exception {
        new KitchenServiceApplication().run(args);
    }
}
