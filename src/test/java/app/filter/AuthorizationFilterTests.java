package app.filter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.config.UserContext;
import app.config.UserPrincipal;
import app.service.AuthService;
import io.quarkus.logging.Log;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AuthorizationFilter")
@ExtendWith(MockitoExtension.class)
public class AuthorizationFilterTests {

    static MockedStatic<Log> logs;

    @Mock
    transient AuthService authService;

    @InjectMocks
    transient AuthorizationFilter authFilter;

    @BeforeAll
    static void beforeAll() {
        logs = mockStatic(Log.class);
    }

    @Test
    @DisplayName("#filter should create the security context")
    void filter() throws Exception {
        ContainerRequestContext request = mock(ContainerRequestContext.class);
        UserPrincipal userPrincipal = new UserPrincipal("username", "xxx.yyy.zzz", List.of());
        when(request.getHeaders()).thenReturn(new MultivaluedHashMap<>());
        when(request.getUriInfo()).thenReturn(mock(UriInfo.class));
        when(authService.validate(any())).thenReturn(userPrincipal);

        authFilter.filter(request);

        verify(authService).validate(any());
        verify(request).setSecurityContext(any(UserContext.class));
    }

    @AfterAll
    static void afterAll() {
        logs.close();
    }
}
