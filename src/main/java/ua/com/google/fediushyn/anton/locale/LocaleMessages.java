package ua.com.google.fediushyn.anton.locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class LocaleMessages {
    public static String getLocaleMessage(MessageSource messageSource, String[] params, String message, String messageDefault){
        String returnMessage = "";
        try {
            returnMessage = messageSource.getMessage(message, params, new Locale("ru"));
        } catch (NoSuchMessageException e) {
            returnMessage = messageDefault;
        }
        return returnMessage;
    }

    public static String getLocaleMessage(MessageSource messageSource, String[] params, String message, String messageDefault, String fileName){
        String returnMessage = "";
        try {
            returnMessage = messageSource.getMessage(message, params, new Locale("ru"));
        } catch (NoSuchMessageException e) {
            returnMessage = messageDefault;
        }
        if (!fileName.isEmpty()) {
            returnMessage = String.format(returnMessage, fileName);
        }
        return returnMessage;
    }
}
