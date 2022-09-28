package app.filter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.config.UserContext;
import app.dto.UserPrincipal;
import app.service.AuthService;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AuthorizationFilter")
@ExtendWith(MockitoExtension.class)
public class AuthorizationFilterTests {

    @Mock
    transient AuthService authService;

    @InjectMocks
    transient AuthorizationFilter authFilter;

    @Test
    @DisplayName("#filter should create the security context")
    void filter() throws Exception {
        ContainerRequestContext request = mock(ContainerRequestContext.class);
        UserPrincipal userPrincipal = new UserPrincipal("username", List.of(), "xxx.yyy.zzz");
        when(request.getHeaders()).thenReturn(new MultivaluedHashMap<>());
        when(authService.validate(any())).thenReturn(userPrincipal);

        authFilter.filter(request);

        verify(authService).validate(any());
        verify(request).setSecurityContext(any(UserContext.class));
    }
}
