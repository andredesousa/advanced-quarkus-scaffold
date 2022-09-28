package app;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import io.quarkus.runtime.Quarkus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@DisplayName("Application")
public class ApplicationTests {

    static MockedStatic<Quarkus> quarkusApplicationMock;

    @BeforeAll
    static void beforeAll() {
        quarkusApplicationMock = mockStatic(Quarkus.class);
    }

    @Test
    void contextLoads() {
        quarkusApplicationMock.when(() -> Quarkus.run(new String[] {})).thenCallRealMethod();

        Application.main(new String[] {});

        quarkusApplicationMock.verify(() -> Quarkus.run(new String[] {}), times(1));
    }
}
