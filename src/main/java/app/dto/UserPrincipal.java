package app.dto;

import java.security.Principal;
import java.util.List;
import lombok.Getter;

@Getter
public class UserPrincipal implements Principal {

    private String username;

    private String accessToken;

    private List<String> authorities;

    public UserPrincipal(String username, List<String> authorities, String token) {
        this.authorities = authorities;
        this.accessToken = token;
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
