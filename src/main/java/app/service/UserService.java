package app.service;

import app.dto.UserDto;
import app.entity.User;
import app.mapper.EntityToDtoMapper;
import app.repository.UserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    @Inject
    protected transient UserRepository userRepository;

    @Inject
    protected transient EntityToDtoMapper mapper;

    private final transient Hasher passwordEncoder = BCrypt.with(BCrypt.Version.VERSION_2B);

    /**
     * Gets a list of users.
     * @return A list of users.
     */
    public List<UserDto> findAll() {
        List<User> users = userRepository.listAll();

        return mapper.usersToUsersDto(users);
    }

    /**
     * Gets a user by id.
     * @param id - The id of the user.
     * @return The user.
     */
    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findByIdOptional(id);

        return mapper.userToUserDto(mapper.unwrap(user));
    }

    /**
     * Creates a user.
     * @param user - The user to insert.
     * @return The inserted user.
     */
    public UserDto create(UserDto user) {
        User newUser = mapper.userDtoToUser(user);
        newUser.setPassword(passwordEncoder.hashToString(10, user.password.toCharArray()));
        userRepository.persist(newUser);

        return mapper.userToUserDto(newUser);
    }

    /**
     * Updates a user.
     * @param id - The id of the user.
     * @param user - The user to update.
     * @return The updated user.
     */
    public UserDto update(Long id, UserDto user) {
        User updatedUser = userRepository.findByIdOptional(user.id).orElseThrow(() -> new NotFoundException());
        updatedUser.setUsername(user.username);
        updatedUser.setPassword(passwordEncoder.hashToString(10, user.password.toCharArray()));
        updatedUser.setEmail(user.email);
        userRepository.persist(updatedUser);

        return mapper.userToUserDto(updatedUser);
    }

    /**
     * Deletes a user by id.
     * @param id - The id of the user to delete.
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
