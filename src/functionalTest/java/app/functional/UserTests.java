package app.functional;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import app.entity.User;
import app.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@QuarkusTest
@DisplayName("Functional Tests")
public class UserTests {

    @InjectMock
    transient UserRepository userRepository;

    transient String bearerToken =
        "Bearer eyJhbGciOiJIUzUxMiJ9." +
        "eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTYzMzc5NDQwMSwiZXhwIjoxOTQ5MTU0NDAxLCJhdXRob3JpdGllcyI6W119." +
        "KR1DBB-ui8ycBhIcRhzOwhcqCNC2DTy5aDYlKeARg1_I0-Aa_KiBHvfZEJbsH4oO3vQxn5yaHmnxtIrlJOtoiQ";

    @Test
    @DisplayName("/user (GET)")
    void getAllUsers() {
        when(userRepository.listAll()).thenReturn(List.of());

        given().headers(Map.of(AUTHORIZATION, bearerToken)).when().get("/user").then().statusCode(200);
    }

    @Test
    @DisplayName("/user/{id} (GET)")
    void getUserById() {
        when(userRepository.findByIdOptional(1L)).thenReturn(Optional.of(new User()));

        given().headers(Map.of(AUTHORIZATION, bearerToken)).when().get("/user/1").then().statusCode(200);
    }

    @Test
    @DisplayName("/user (POST)")
    void addUser() {
        String user = "{\"username\":\"username\",\"password\":\"12345678\",\"email\":\"user@api.com\"}";
        doNothing().when(userRepository).persist(any(User.class));

        given()
            .headers(Map.of(AUTHORIZATION, bearerToken, CONTENT_TYPE, JSON))
            .body(user)
            .when()
            .post("/user")
            .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("/user/{id} (PUT)")
    void updateUser() {
        String user = "{\"id\":\"1\",\"username\":\"username\",\"password\":\"12345678\",\"email\":\"user@api.com\"}";
        when(userRepository.findByIdOptional(1L)).thenReturn(Optional.of(new User()));
        doNothing().when(userRepository).persist(any(User.class));

        given()
            .headers(Map.of(AUTHORIZATION, bearerToken, CONTENT_TYPE, JSON))
            .body(user)
            .when()
            .put("/user/1")
            .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("/user/{id} (DELETE)")
    void deleteUser() {
        when(userRepository.deleteById(1L)).thenReturn(true);

        given().headers(Map.of(HttpHeaders.AUTHORIZATION, bearerToken)).when().delete("/user/1").then().statusCode(204);
    }
}
