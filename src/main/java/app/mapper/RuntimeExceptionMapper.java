package app.mapper;

import app.dto.ErrorDto;
import java.time.LocalDateTime;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        ErrorDto error = new ErrorDto(
            LocalDateTime.now(),
            Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            e.getLocalizedMessage()
        );

        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}
