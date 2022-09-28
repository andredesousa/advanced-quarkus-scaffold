package app.mapper;

import app.dto.ErrorResponse;
import java.util.Date;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        ErrorResponse error = new ErrorResponse(
            new Date(),
            Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            e.getLocalizedMessage()
        );

        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}
