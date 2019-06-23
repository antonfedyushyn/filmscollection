package ua.com.google.fediushyn.anton.upload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFile {
    String uploadSingleImageFile(MultipartFile file);

    List<String> UploadMultiImageFiles(MultipartFile file);

    String uploadVideoFile(MultipartFile file);


}
