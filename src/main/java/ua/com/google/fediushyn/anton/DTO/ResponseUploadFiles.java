package ua.com.google.fediushyn.anton.DTO;

public class ResponseUploadFiles {
    private boolean result;
    private String message;
    private String filesPathes;
    private int countFiles;

    public ResponseUploadFiles() {
    }

    public ResponseUploadFiles(boolean result, String message, String filesPathes, int countFiles) {
        this.result = result;
        this.message = message;
        this.filesPathes = filesPathes;
        this.countFiles = countFiles;
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

    public String getFilesPathes() {
        return filesPathes;
    }

    public void setFilesPathes(String filesPathes) {
        this.filesPathes = filesPathes;
    }

    public int getCountFiles() {
        return countFiles;
    }

    public void setCountFiles(int countFiles) {
        this.countFiles = countFiles;
    }
}
