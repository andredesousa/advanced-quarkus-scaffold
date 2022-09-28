package app.config;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MessageSource {

    private ResourceBundle resource;

    private Locale locale;

    public String getMessage(String code, Object[] args, Locale locale) {
        this.resource = this.resource == null || this.locale != locale
                ? ResourceBundle.getBundle("i18n.messages", locale)
                : this.resource;
        this.locale = locale;

        return MessageFormat.format(resource.getString(code), args);
    }
}
