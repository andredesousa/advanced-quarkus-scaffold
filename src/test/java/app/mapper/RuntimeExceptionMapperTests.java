package app.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import app.dto.ErrorDto;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RuntimeExceptionMapper")
@ExtendWith(MockitoExtension.class)
public class RuntimeExceptionMapperTests {

    @InjectMocks
    transient RuntimeExceptionMapper mapper;

    @Test
    @DisplayName("#toResponse returns 404 error")
    void toResponseReturnsErrorDtoForNotFoundException() {
        ErrorDto error = new ErrorDto(null, 404, "Not Found", "HTTP 404 Not Found");

        assertThat(mapper.toResponse(new NotFoundException()))
            .usingRecursiveComparison()
            .ignoringFields("reason", "entity.timestamp")
            .isEqualTo(Response.status(Status.NOT_FOUND).entity(error).build());
    }

    @Test
    @DisplayName("#toResponse returns 500 error")
    void toResponseReturnsErrorDtoForGenericException() {
        ErrorDto error = new ErrorDto(null, 500, "Internal Server Error", "HTTP 500 Internal Server Error");

        assertThat(mapper.toResponse(new InternalServerErrorException()))
            .usingRecursiveComparison()
            .ignoringFields("reason", "entity.timestamp")
            .isEqualTo(Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build());
    }
}
