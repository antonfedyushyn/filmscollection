package ua.com.google.fediushyn.anton.DTO;

public class ResponseDelete {
    private boolean result;
    private String message;

    public ResponseDelete() {
    }

    public ResponseDelete(boolean result) {
        this.result = result;
        this.message = "";
    }

    public ResponseDelete(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseDelete{" +
                "result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
