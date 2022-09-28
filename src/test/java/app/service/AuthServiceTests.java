package app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import app.dto.AuthDto;
import app.dto.UserDto;
import app.dto.UserPrincipal;
import app.mapper.EntityToDtoMapper;
import app.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AuthService")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    transient UserRepository userRepository;

    @InjectMocks
    transient AuthService authService;

    transient EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    @BeforeEach
    void beforeEach() {
        authService.jwtSecret = "<ApiSecretKey>";
        authService.jwtExpiration = 3600L;
    }

    @Test
    @DisplayName("#login returns the user session")
    void login() {
        String password = "$2b$10$jkzR/NI9PCgA3UXhx5T6WOqPJkzhTGAJY/5Z0txIfRt57ThjqfSOe";
        UserDto user = new UserDto(1L, "username", password, "username@email");
        AuthDto credentials = new AuthDto("username", "admin");
        when(userRepository.findByUsername(credentials.username)).thenReturn(Optional.of(mapper.userDtoToUser(user)));

        assertThat(authService.login(credentials)).isInstanceOf(UserPrincipal.class);
    }

    @Test
    @DisplayName("#refresh returns a new jwt token")
    void refresh() {
        assertThat(authService.refresh("username", List.of())).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("#validate returns user credentials")
    void validate() {
        String token =
            "eyJhbGciOiJIUzUxMiJ9." +
            "eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTYzMzc5NDQwMSwiZXhwIjoxOTQ5MTU0NDAxLCJhdXRob3JpdGllcyI6W119." +
            "KR1DBB-ui8ycBhIcRhzOwhcqCNC2DTy5aDYlKeARg1_I0-Aa_KiBHvfZEJbsH4oO3vQxn5yaHmnxtIrlJOtoiQ";
        UserPrincipal auth = new UserPrincipal("username", "", List.of(), token);

        assertThat(authService.validate("Bearer " + token)).usingRecursiveComparison().isEqualTo(auth);
    }
}
