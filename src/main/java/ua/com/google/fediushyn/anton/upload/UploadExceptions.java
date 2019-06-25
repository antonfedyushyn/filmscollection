package ua.com.google.fediushyn.anton.upload;

public class UploadExceptions extends Exception {

    private String message = "";
    private String defaultMessage = "";
    private String fileName = "";

    public UploadExceptions(String message) {
        super();
        this.message = message;
        this.defaultMessage = message;
    }

    public UploadExceptions(String message, String defaultMessage) {
        super();
        this.message = message;
        this.defaultMessage = defaultMessage;
    }

    public UploadExceptions(String message, String defaultMessage, String fileName) {
        super();
        this.message = message;
        this.defaultMessage = defaultMessage;
        this.fileName = fileName;
    }

    public UploadExceptions(String message, String defaultMessage, Throwable cause) {
        super(cause);
        this.message = message;
        this.defaultMessage = defaultMessage;
    }

    public UploadExceptions(String message, String defaultMessage, Throwable cause, String fileName) {
        super(cause);
        this.message = message;
        this.defaultMessage = defaultMessage;
        this.fileName = fileName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getFileName() {
        return fileName;
    }
}
