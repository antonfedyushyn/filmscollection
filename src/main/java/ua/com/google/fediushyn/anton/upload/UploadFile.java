package ua.com.google.fediushyn.anton.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface UploadFile {
    String uploadSingleImageFile(MultipartFile file, Boolean isPoser) throws UploadExceptions;

    List<String> uploadMultiImageFiles(MultipartFile[] file) throws UploadExceptions;

    String uploadVideoFile(MultipartFile file) throws UploadExceptions;

    byte[] getImageFile(String fileName) throws UploadExceptions;

    File getVideoFile(String fileName) throws UploadExceptions;

}
