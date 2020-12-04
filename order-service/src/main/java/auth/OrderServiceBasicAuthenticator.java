package auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;
public class OrderServiceBasicAuthenticator implements Authenticator<BasicCredentials, BasicUser> {
    public Optional authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (!credentials.getUsername().isEmpty() && "secret".equals(credentials.getPassword())) {
            return Optional.ofNullable(new BasicUser(credentials.getUsername()));
        }
        return Optional.empty();
    }
}
