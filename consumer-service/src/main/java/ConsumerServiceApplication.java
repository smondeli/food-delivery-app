import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class ConsumerServiceApplication extends Application<ConsumerServiceConfiguration> {
    public void run(ConsumerServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
    }

    public static void main(String[] args) throws Exception {
        new ConsumerServiceApplication().run(args);
    }
}
