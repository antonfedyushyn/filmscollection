package ua.com.google.fediushyn.anton.contorllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.google.fediushyn.anton.DTO.ResponseDelete;
import ua.com.google.fediushyn.anton.DTO.ResponseUploadFile;
import ua.com.google.fediushyn.anton.DTO.ResponseUploadFiles;
import ua.com.google.fediushyn.anton.consts.ConfigProperties;
import ua.com.google.fediushyn.anton.consts.UploadProperies;
import ua.com.google.fediushyn.anton.upload.UploadExceptions;
import ua.com.google.fediushyn.anton.upload.UploadFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadContorller {
    private final FilmsController filmsController;
    private final UploadProperies uploadProperies;

    @Autowired
    UploadContorller(FilmsController filmsController,
                     UploadProperies uploadProperies){
        this.filmsController = filmsController;
        this.uploadProperies = uploadProperies;
    }

    @PostMapping("/uploadPosterFile")
    @ResponseBody
    public ResponseUploadFile uploadPosterFile(@RequestParam("posterFile") MultipartFile file) {

        Boolean result = false;
        String resMessage;
        String fileName = "";
        String filePath;


        UploadFiles upload = new UploadFiles(uploadProperies);
        try {
            fileName = upload.uploadSingleImageFile(file, true);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.file",
                    "File %s uploaded successfully!", fileName);
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }

        filePath = "/imageFile/"+fileName;

        return new ResponseUploadFile(result, resMessage, filePath, fileName);
    }

    @PostMapping("/uploadImageFiles")
    @ResponseBody
    public ResponseUploadFiles uploadImageFiles(@RequestParam("imageFiles") MultipartFile[] file) {

        Boolean result = false;
        String resMessage;
        List<String> filesName = null;
        List<String> filesPathes = new ArrayList<>();

        UploadFiles upload = new UploadFiles(uploadProperies);
        try {
            filesName = upload.uploadMultiImageFiles(file);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.files",
                    "Files uploaded successfully!");
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }

        if (!filesName.isEmpty()) {
            for (String filePath : filesName) {
                filesPathes.add("/imageFile/" + filePath);
            }
        }

        return new ResponseUploadFiles(result, resMessage, String.join(",", filesPathes), file.length);
    }

    @PostMapping("/uploadVideoFile")
    @ResponseBody
    public ResponseUploadFile uploadVideoFile(@RequestParam("videoFile") MultipartFile file) {

        Boolean result = false;
        String resMessage;
        String fileName = "";
        String pathFile;

        UploadFiles upload = new UploadFiles(uploadProperies);
        try {
            fileName = upload.uploadVideoFile(file);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.file",
                    "File %s uploaded successfully!", fileName);
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }

        pathFile = "/videosrc/"+fileName;

        return new ResponseUploadFile(result, resMessage, pathFile, fileName);
    }

    @GetMapping(value = "/imageFile/{name}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable("name") String fileName) {
        byte[] imageContext = null;
        HttpStatus status = HttpStatus.OK;
        UploadFiles uploadFiles = new UploadFiles(uploadProperies);
        try{
            imageContext = uploadFiles.getImageFile(fileName);
        } catch (UploadExceptions e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        MediaType mediaType = UploadFiles.getFileImageMediaType(uploadProperies.getFiles() + File.separator + uploadProperies.getImages() + File.separator + fileName);

        HttpHeaders headers = new HttpHeaders();
        if (mediaType != null) {
            headers.setContentType(mediaType);
        }
        return new ResponseEntity<>(imageContext, headers, status);
    }

    @GetMapping(value = "/videosrc/{name}", produces = "video/mp4")
    @ResponseBody
    public FileSystemResource videoSource(@PathVariable("name") String fileName) {
        try {
            File video = (new UploadFiles(uploadProperies)).getVideoFile(fileName);
            return new FileSystemResource(video);
        } catch (UploadExceptions e){
          return null;
        }
    }

    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseDelete deleteFile(@RequestParam("fileName") String fileName){
        return (new UploadFiles(uploadProperies)).deleteImageFile(fileName);
    }
}
