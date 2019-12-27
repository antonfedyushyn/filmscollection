package ua.com.google.fediushyn.anton.DTO;

public class ResponseUploadFile {
    private boolean result;
    private String message;
    private String path;
    private String fileName;

    public ResponseUploadFile() {
    }

    public ResponseUploadFile(boolean result, String message, String path, String fileName) {
        this.result = result;
        this.message = message;
        this.path = path;
        this.fileName = fileName;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
