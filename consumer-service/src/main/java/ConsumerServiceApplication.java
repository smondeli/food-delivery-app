import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import resources.ConsumerResource;

public class ConsumerServiceApplication extends Application<ConsumerServiceConfiguration> {
    public void run(ConsumerServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
        environment.jersey().register(new ConsumerResource());
    }

    public static void main(String[] args) throws Exception {
        new ConsumerServiceApplication().run(args);
    }
}
