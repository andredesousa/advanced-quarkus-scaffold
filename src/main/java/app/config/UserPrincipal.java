package app.config;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class UserPrincipal implements Principal {

    private transient String username;

    private transient String accessToken;

    private transient List<String> authorities;

    public UserPrincipal(String username, String token, List<String> authorities) {
        this.authorities = new ArrayList<String>(authorities);
        this.accessToken = token;
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public List<String> getAuthorities() {
        return new ArrayList<String>(authorities);
    }
}
