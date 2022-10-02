package app.resource;

import app.config.UserPrincipal;
import app.dto.AuthDto;
import app.service.AuthService;
import io.quarkus.security.Authenticated;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("")
public class AuthResource {

    @Inject
    protected transient AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    public UserPrincipal login(@Valid AuthDto auth) {
        return authService.login(auth);
    }

    @POST
    @Path("/refresh")
    @Authenticated
    public String refresh(@Context SecurityContext ctx) {
        return authService.refresh(
            ctx.getUserPrincipal().getName(),
            ((UserPrincipal) ctx.getUserPrincipal()).getAuthorities()
        );
    }
}
