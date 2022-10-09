package app.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

import app.dto.ErrorDto;
import io.quarkus.logging.Log;
import java.time.LocalDateTime;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RuntimeExceptionMapper")
@ExtendWith(MockitoExtension.class)
public class RuntimeExceptionMapperTests {

    static MockedStatic<Log> logs;

    static MockedStatic<LocalDateTime> date;

    @InjectMocks
    transient RuntimeExceptionMapper mapper;

    @BeforeAll
    static void beforeAll() {
        logs = mockStatic(Log.class);
        date = mockStatic(LocalDateTime.class);
        date.when(() -> LocalDateTime.now()).thenReturn(null);
    }

    @Test
    @DisplayName("#toResponse returns 500 error")
    void toResponse() {
        ErrorDto error = new ErrorDto(null, 500, "Internal Server Error", "HTTP 500 Internal Server Error");

        assertThat(mapper.toResponse(new InternalServerErrorException()))
            .usingRecursiveComparison()
            .isEqualTo(Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build());
    }

    @AfterAll
    static void afterAll() {
        logs.close();
        date.close();
    }
}
