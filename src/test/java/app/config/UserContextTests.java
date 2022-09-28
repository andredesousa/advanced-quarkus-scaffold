package app.config;

import static org.assertj.core.api.Assertions.assertThat;

import app.dto.UserPrincipal;
import java.util.List;
import javax.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("MessageSource")
@ExtendWith(MockitoExtension.class)
public class UserContextTests {

    transient UserPrincipal user;

    transient UserContext ctx;

    @BeforeEach
    void beforeEach() {
        user = new UserPrincipal("username", List.of("ADMIN"), "xxx.yyy.xxx");
        ctx = new UserContext(user);
    }

    @Test
    @DisplayName("#getUserPrincipal returns the user principal")
    void getUserPrincipal() {
        assertThat(ctx.getUserPrincipal()).isEqualTo(user);
    }

    @Test
    @DisplayName("#isUserInRole returns true for 'ADMIN' role")
    void isUserInRole() {
        assertThat(ctx.isUserInRole("ADMIN")).isEqualTo(true);
    }

    @Test
    @DisplayName("#isUserInRole returns false for 'OTHER' role")
    void isUserOutRole() {
        assertThat(ctx.isUserInRole("OTHER")).isEqualTo(false);
    }

    @Test
    @DisplayName("#isSecure returns false for HTTP")
    void isSecure() {
        assertThat(ctx.isSecure()).isEqualTo(false);
    }

    @Test
    @DisplayName("#getAuthenticationScheme returns 'BASIC'")
    void getAuthenticationScheme() {
        assertThat(ctx.getAuthenticationScheme()).isEqualTo(SecurityContext.BASIC_AUTH);
    }
}
