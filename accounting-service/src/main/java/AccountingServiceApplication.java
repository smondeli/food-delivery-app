import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import resources.AccountingResource;

public class AccountingServiceApplication extends Application<AccountingServiceConfiguration> {
    public void run(AccountingServiceConfiguration configuration, Environment environment) throws Exception {
        System.out.println(configuration.getApplicationName());
        environment.jersey().register(new AccountingResource());
    }

    public static void main(String[] args) throws Exception {
        new AccountingServiceApplication().run(args);
    }
}
