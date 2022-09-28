package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.security.Principal;
import java.util.List;
import lombok.Getter;

@Getter
public class UserPrincipal implements Principal {

    private String username;

    private String password;

    private String accessToken;

    private List<String> authorities;

    public UserPrincipal(String username, String password, List<String> authorities, String token) {
        this.authorities = authorities;
        this.accessToken = token;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getName() {
        return username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
