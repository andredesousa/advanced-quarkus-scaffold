package app.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.dto.ErrorDto;
import java.time.LocalDateTime;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ErrorResponseFilter")
@ExtendWith(MockitoExtension.class)
public class ErrorResponseFilterTests {

    static MockedStatic<LocalDateTime> date;

    @InjectMocks
    transient ErrorResponseFilter errorFilter;

    @BeforeAll
    static void beforeAll() {
        date = mockStatic(LocalDateTime.class);
        date.when(() -> LocalDateTime.now()).thenReturn(null);
    }

    @Test
    @DisplayName("#filter returns 404 error")
    void filter() throws Exception {
        ContainerResponseContext response = mock(ContainerResponseContext.class);
        when(response.getStatus()).thenReturn(404);
        when(response.getStatusInfo()).thenReturn(Status.NOT_FOUND);
        when(response.getHeaders()).thenReturn(new MultivaluedHashMap<>());

        errorFilter.filter(null, response);

        verify(response).setEntity(new ErrorDto(null, 404, "Not Found", "Not Found"));
    }

    @AfterAll
    static void afterAll() {
        date.close();
    }
}
