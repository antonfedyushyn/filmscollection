package ua.com.google.fediushyn.anton.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class AddUserException extends FilmException {
    final private String[] params = {};

    public AddUserException() {
        super();
    }

    public AddUserException(String message) {
        super(message);
    }

    public AddUserException(String message, String messageDefault){
        super(message, messageDefault);
    }
}
