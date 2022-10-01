package app.config;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

public class UserContext implements SecurityContext {

    private transient UserPrincipal user;

    public UserContext(UserPrincipal userPrincipal) {
        user = userPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return user.getAuthorities().contains(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
