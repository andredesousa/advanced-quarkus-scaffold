package smoke;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DisplayName("Smoke Tests")
public class HealthTests {

    static Network NETWORK = Network.newNetwork();

    static PostgreSQLContainer<?> PG_CONTAINER = new PostgreSQLContainer<>("postgres");

    static GenericContainer<?> APP_CONTAINER = new GenericContainer<>(DockerImageName.parse("quarkus-api"));

    @BeforeAll
    static void beforeAll() {
        PG_CONTAINER
            .withNetwork(NETWORK)
            .withNetworkAliases("postgres")
            .withExposedPorts(5432)
            .withDatabaseName("quarkus")
            .withUsername("root")
            .withPassword("secret")
            .waitingFor(Wait.forListeningPort())
            .start();

        APP_CONTAINER
            .withNetwork(NETWORK)
            .withExposedPorts(8080)
            .withEnv("QUARKUS_PROFILE", "prod")
            .withEnv("DB_URL", "jdbc:postgresql://postgres:5432/quarkus")
            .withEnv("DB_USER", "root")
            .withEnv("DB_PASSWORD", "secret")
            .withEnv("DB_VALIDATE", "none")
            .withEnv("JWT_SECRET", "<ApiSecretKey>")
            .withEnv("JWT_EXPIRATION", "3600")
            .waitingFor(Wait.forListeningPort())
            .start();
    }

    @AfterAll
    static void afterAll() {
        PG_CONTAINER.stop();
        APP_CONTAINER.stop();
    }

    @Test
    @DisplayName("/health endpoint")
    void healthEndpoint() throws Exception {
        String address = "http://localhost:" + APP_CONTAINER.getMappedPort(8080) + "/health";
        String expected =
            "{status=UP, components={db={status=UP}, diskSpace={status=UP}, " +
            "livenessState={status=UP}, ping={status=UP}, readinessState={status=UP}}}";

        given().when().get(address).then().statusCode(200).body(is(expected));
    }
}
