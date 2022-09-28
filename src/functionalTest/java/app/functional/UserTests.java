package app.functional;

import static org.mockito.Mockito.when;
import static io.restassured.RestAssured.given;

import app.dto.UserDto;
import app.entity.User;
import app.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@QuarkusTest
@DisplayName("Functional Tests")
public class UserTests {

    @Mock
    transient UserRepository userRepository;

    transient String bearerToken =
        "Bearer eyJhbGciOiJIUzUxMiJ9." +
        "eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTYzMzc5NDQwMSwiZXhwIjoxOTQ5MTU0NDAxLCJhdXRob3JpdGllcyI6W119." +
        "KR1DBB-ui8ycBhIcRhzOwhcqCNC2DTy5aDYlKeARg1_I0-Aa_KiBHvfZEJbsH4oO3vQxn5yaHmnxtIrlJOtoiQ";

    @Test
    @DisplayName("/user (GET)")
    void getAllUsers() {
        given()
            .headers(Map.of(HttpHeaders.AUTHORIZATION, bearerToken))
            .when().get("/user")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/user/{id} (GET)")
    void getUserById() {
        when(userRepository.findByIdOptional(1L)).thenReturn(Optional.of(new User()));

        given()
            .headers(Map.of(HttpHeaders.AUTHORIZATION, bearerToken))
            .when().get("/user/1")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/user (POST)")
    void addUser() {
        UserDto user = new UserDto(null, "username", "password", "email@email");

        given()
            .headers(Map.of(HttpHeaders.AUTHORIZATION, bearerToken)).body(user)
            .when().post("/user")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/user/{id} (PUT)")
    void updateUser() {
        UserDto user = new UserDto(1L, "username", "password", "email@email");

        given()
            .headers(Map.of(HttpHeaders.AUTHORIZATION, bearerToken)).body(user)
            .when().post("/user/1")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/user/{id} (DELETE)")
    void deleteUser() {
        given()
            .headers(Map.of(HttpHeaders.AUTHORIZATION, bearerToken))
            .when().delete("/user/1")
            .then().statusCode(200);
    }
}
