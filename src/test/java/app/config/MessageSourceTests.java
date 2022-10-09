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
    @DisplayName("#getMessage returns a translated message for default locale")
    void getEnglishMessage() {
        String message = new MessageSource().getMessage("http.status.404", null, Locale.getDefault());

        assertThat(message).isEqualTo("Not Found");
    }

    @Test
    @DisplayName("#getMessage returns a translated message for Germany locale")
    void getGermanMessage() {
        String message = new MessageSource().getMessage("http.status.404", null, Locale.GERMANY);

        assertThat(message).isEqualTo("Nicht Gefunden");
    }
}
