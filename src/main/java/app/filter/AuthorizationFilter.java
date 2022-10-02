package app.filter;

import app.config.UserContext;
import app.config.UserPrincipal;
import app.service.AuthService;
import io.quarkus.logging.Log;
import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {

    @Inject
    protected transient AuthService authService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            String bearerToken = requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            UserPrincipal user = authService.validate(bearerToken);
            requestContext.setSecurityContext(new UserContext(user));
        } catch (Exception ex) {
            Log.info("Request from unauthenticated user");
        } finally {
            Log.infof("Request %s %s", requestContext.getMethod(), requestContext.getUriInfo().getRequestUri());
        }
    }
}
