package ua.com.google.fediushyn.anton.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class FilmException extends Exception {
    private String message = "";
    private String messageDefault = "";
    private String value = "";

    public FilmException() {
    }

    public FilmException(String message) {
        this.message = message;
        this.messageDefault = message;
    }

    public FilmException(String message, String messageDefault) {
        this.message = message;
        this.messageDefault = messageDefault;
    }

    public FilmException(String message, String messageDefault, String value) {
        this.message = message;
        this.messageDefault = messageDefault;
        this.value = value;
    }

    public FilmException(String message, Throwable cause, String message1, String messageDefault) {
        super(message, cause);
        this.message = message1;
        this.messageDefault = messageDefault;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getMessageDefault() {
        return messageDefault;
    }

    public String getValue() {
        return value;
    }
}
