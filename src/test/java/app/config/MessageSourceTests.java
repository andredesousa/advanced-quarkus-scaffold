package app.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("MessageSource")
@ExtendWith(MockitoExtension.class)
public class MessageSourceTests {

    @Test
    @DisplayName("#getMessage returns a translated message")
    void getMessage() {
        MessageSource messageSource = new MessageSource();
        String message = messageSource.getMessage("http.status.404", null, Locale.US);

        assertThat(message).isEqualTo("Not Found");
    }
}
