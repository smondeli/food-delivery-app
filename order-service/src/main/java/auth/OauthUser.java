package auth;

import java.security.Principal;

public class OauthUser implements Principal {
    private String username;

    public OauthUser(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
