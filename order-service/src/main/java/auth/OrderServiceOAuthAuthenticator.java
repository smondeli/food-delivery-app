package auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;

public class OrderServiceOAuthAuthenticator implements Authenticator<String, OauthUser> {
    @Override
    public Optional<OauthUser> authenticate(String s) throws AuthenticationException {
        if("sree".equals(s)){
            return Optional.ofNullable(new OauthUser(s));
        }
        return Optional.empty();
    }
}
