package app.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import app.config.UserContext;
import app.config.UserPrincipal;
import app.dto.AuthDto;
import app.service.AuthService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AuthResource")
@ExtendWith(MockitoExtension.class)
public class AuthResourceTests {

    @Mock
    transient AuthService authService;

    @InjectMocks
    transient AuthResource authResource;

    @Test
    @DisplayName("#login returns the user session")
    void login() {
        AuthDto auth = new AuthDto("username", "password");
        UserPrincipal userSession = new UserPrincipal(auth.getUsername(), "xxx.yyy.zzzz", List.of());

        when(authService.login(auth)).thenReturn(userSession);

        assertThat(authResource.login(auth)).isEqualTo(userSession);
    }

    @Test
    @DisplayName("#refresh returns a new token")
    void refresh() {
        String token = "xxx.yyy.zzz";
        UserPrincipal user = new UserPrincipal("username", token, List.of());

        when(authService.refresh(user.getName(), List.of())).thenReturn(token);

        assertThat(authResource.refresh(new UserContext(user))).isEqualTo(token);
    }
}
