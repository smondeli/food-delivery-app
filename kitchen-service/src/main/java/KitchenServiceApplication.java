import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class KitchenServiceApplication extends Application<KitchenServiceConfiguration> {
    public void run(KitchenServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
    }

    public static void main(String[] args) throws Exception {
        new KitchenServiceApplication().run(args);
    }
}
