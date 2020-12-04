package auth;

import java.security.Principal;

public class BasicUser implements Principal {
    private String username;

    public BasicUser(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
