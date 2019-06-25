package ua.com.google.fediushyn.anton.exceptions;

public class AddUserException extends FilmException {
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
