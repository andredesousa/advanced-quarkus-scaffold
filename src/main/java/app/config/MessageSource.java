package app.config;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MessageSource {

    private transient ResourceBundle resource;

    private transient Locale locale;

    public String getMessage(String code, Object[] args, Locale locale) {
        this.resource =
            this.resource != null && locale.equals(this.locale)
                ? this.resource
                : ResourceBundle.getBundle("i18n.messages", locale);
        this.locale = locale;

        return MessageFormat.format(resource.getString(code), args);
    }
}
