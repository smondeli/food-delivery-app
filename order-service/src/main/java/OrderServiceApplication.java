import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class OrderServiceApplication extends Application<OrderServiceConfiguration> {
    public void run(OrderServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
    }

    public static void main(String[] args) throws Exception {
        new OrderServiceApplication().run(args);
    }
}
