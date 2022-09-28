package app.resource;

import app.dto.UserDto;
import app.service.UserService;
import io.quarkus.security.Authenticated;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Authenticated
@Path("/user")
public class UserResource {

    @Inject
    protected transient UserService userService;

    @GET
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    public UserDto findById(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @POST
    @Transactional
    public UserDto create(@Valid UserDto user) {
        return userService.create(user);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public UserDto update(@PathParam("id") Long id, @Valid UserDto user) {
        return userService.update(id, user);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        userService.delete(id);
    }
}
