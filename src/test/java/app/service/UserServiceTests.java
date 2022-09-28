package app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.dto.UserDto;
import app.entity.User;
import app.mapper.EntityToDtoMapper;
import app.repository.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UserService")
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    transient UserRepository userRepository;

    @Spy
    transient EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    @InjectMocks
    transient UserService userService;

    @Test
    @DisplayName("#findAll returns an array of users")
    void findAll() {
        when(userRepository.listAll()).thenReturn(new ArrayList<>());

        assertThat(userService.findAll()).isEqualTo(new ArrayList<>());
        verify(mapper).usersToUsersDto(new ArrayList<>());
    }

    @Test
    @DisplayName("#findById returns an user")
    void findById() {
        Optional<User> user = Optional.of(new User());
        when(userRepository.findByIdOptional(1L)).thenReturn(user);

        assertThat(userService.findById(1L)).isInstanceOf(UserDto.class);
    }

    @Test
    @DisplayName("#create returns a new user")
    void createUser() {
        UserDto user = new UserDto(null, "username", "password", "email@email");

        assertThat(userService.create(user)).isInstanceOf(UserDto.class);
    }

    @Test
    @DisplayName("#update returns an updated user")
    void updateUser() {
        UserDto user = new UserDto(1L, "username", "password", "email@email");
        when(userRepository.findById(1L)).thenReturn(new User());

        assertThat(userService.update(1L, user)).isInstanceOf(UserDto.class);
    }

    @Test
    @DisplayName("#delete deletes the current user")
    void deleteUser() {
        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }
}
