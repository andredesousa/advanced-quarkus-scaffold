package app.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.dto.UserDto;
import app.service.UserService;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UserResource")
@ExtendWith(MockitoExtension.class)
public class UserResourceTests {

    @Mock
    transient UserService userService;

    @InjectMocks
    transient UserResource userResource;

    @Test
    @DisplayName("#findAll returns an array of users")
    void findAll() {
        when(userService.findAll()).thenReturn(new ArrayList<>());

        assertThat(userResource.findAll()).isEqualTo(new ArrayList<>());
    }

    @Test
    @DisplayName("#findById returns an user")
    void findById() {
        UserDto user = new UserDto();
        when(userService.findById(1L)).thenReturn(user);

        assertThat(userResource.findById(1L)).isEqualTo(user);
    }

    @Test
    @DisplayName("#create returns a new user")
    void createUser() {
        UserDto user = new UserDto();
        when(userService.create(user)).thenReturn(user);

        assertThat(userResource.create(user)).isEqualTo(user);
    }

    @Test
    @DisplayName("#update returns an updated user")
    void updateUser() {
        UserDto user = new UserDto();
        when(userService.update(1L, user)).thenReturn(user);

        assertThat(userResource.update(1L, user)).isEqualTo(user);
    }

    @Test
    @DisplayName("#delete calls deleteUser method")
    void deleteUser() {
        userResource.delete(1L);

        verify(userService).delete(1L);
    }
}
