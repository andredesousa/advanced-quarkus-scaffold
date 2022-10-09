package app.mapper;

import app.dto.ErrorDto;
import io.quarkus.logging.Log;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof ClientErrorException) {
            return ((ClientErrorException) exception).getResponse();
        } else {
            Log.error(Status.INTERNAL_SERVER_ERROR, exception);
            ErrorDto error = new ErrorDto(
                LocalDateTime.now(),
                Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                Optional.ofNullable(exception.getMessage()).orElse(exception.getClass().getSimpleName())
            );

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
