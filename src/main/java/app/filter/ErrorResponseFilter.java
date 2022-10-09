package app.filter;

import app.dto.ErrorDto;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Integer.MAX_VALUE)
public class ErrorResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        if (response.getStatus() > 400 && response.getStatus() < 500) {
            ErrorDto error = new ErrorDto(
                LocalDateTime.now(),
                response.getStatusInfo().getStatusCode(),
                response.getStatusInfo().getReasonPhrase(),
                response.getStatusInfo().getReasonPhrase()
            );

            response.setEntity(error);
            response.getHeaders().putSingle(HttpHeaders.CONTENT_TYPE, "application/json");
        }
    }
}
