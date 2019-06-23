package ua.com.google.fediushyn.anton.contorllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.google.fediushyn.anton.consts.ConfigProperties;
import ua.com.google.fediushyn.anton.upload.UploadExceptions;
import ua.com.google.fediushyn.anton.upload.UploadFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadContorller {
    @Autowired
    private FilmsController filmsController;

    @Autowired
    private ConfigProperties configProp;

    @PostMapping("/uploadPosterFile")
    @ResponseBody
    public String uploadPosterFile(@RequestParam("posterFile") MultipartFile file) {

        Boolean result = false;
        String resMessage = "";
        String fileName = "";


        UploadFiles upload = new UploadFiles();
        try {
            fileName = upload.uploadSingleImageFile(file, true);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.file",
                    "File %s uploaded successfully!", fileName);
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }

        JSONObject json = new JSONObject();
        try {
            json.put("result", result);
            json.put("resMessage", resMessage);
            json.put("path", "/imageFile?fileName="+fileName);
            json.put("fileName", fileName);
            resMessage = json.toString();
        } catch (JSONException e) {
            resMessage = "";
        }
        return resMessage;
    }

    @PostMapping("/uploadImageFiles")
    @ResponseBody
    public String uploadImageFiles(@RequestParam("imageFiles") MultipartFile[] file) {

        Boolean result = false;
        String resMessage = "";
        List<String> fileName = null;


        UploadFiles upload = new UploadFiles();
        try {
            fileName = upload.uploadMultiImageFiles(file);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.files",
                    "Files uploaded successfully!");
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }

        JSONObject json = new JSONObject();
        try {
            json.put("result", result);
            json.put("resMessage", resMessage);
            if (result) {
                List<String> filePathes = new ArrayList<>();
                json.put("filesPathes", String.join(",", filePathes));
                json.put("countFiles", file.length);
            }
            resMessage = json.toString();
        } catch (JSONException e) {
            resMessage = "";
        }
        return resMessage;
    }

    @PostMapping("/uploadVideoFile")
    @ResponseBody
    public String uploadVideoFile(@RequestParam("videoFile") MultipartFile file) {

        Boolean result = false;
        String resMessage = "";
        String fileName = "";

        UploadFiles upload = new UploadFiles();
        try {
            fileName = upload.uploadVideoFile(file);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.file",
                    "File %s uploaded successfully!", fileName);
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }

        JSONObject json = new JSONObject();
        try {
            json.put("result", result);
            json.put("resMessage", resMessage);
            json.put("path", "/videosrc?fileName="+fileName);
            json.put("fileName", fileName);
            resMessage = json.toString();
        } catch (JSONException e) {
            resMessage = "";
        }
        return resMessage;
    }

    @PostMapping("/")
    @ResponseBody
    public String uploadImageFile(@RequestParam("file") MultipartFile file) {
        Boolean result = false;
        String resMessage = "";
        String fileName = "";
        UploadFiles upload = new UploadFiles();
        try {
            fileName = upload.uploadSingleImageFile(file, false);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.file",
                    "File %s uploaded successfully!", fileName);
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }
        JSONObject response = new JSONObject();
        try {
            response.put("resMessage", resMessage);
            response.put("result", result);
            response.put("imageUrl", "/imageFile?fileName="+fileName);
            resMessage = response.toString();
        } catch (JSONException e) {
            resMessage = e.getMessage();
        }
        return resMessage;
    }

    @PostMapping("/uploadImages")
    public String uploadImageFiles(@RequestParam("file") MultipartFile file[],
                                   Model model) {
        Boolean result = false;
        String resMessage = "";
        try {
            List<String> fileName = (new UploadFiles()).uploadMultiImageFiles(file);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.files",
                    "Files uploaded successfully!");
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }
        model.addAttribute("result", result);
        model.addAttribute("resultMessage", resMessage);
        return filmsController.index(model);
    }

    @PostMapping("/uploadPoster")
    public String uploadPosterImage(@RequestParam("file") MultipartFile file) {
        Boolean result = false;
        String resMessage = "";
        try {
            String fileName = (new UploadFiles()).uploadSingleImageFile(file, true);
            result = true;
            resMessage = filmsController.getLocaleMessage("Success.uploadForm.files",
                    "Files uploaded successfully!");
        } catch (UploadExceptions e) {
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getDefaultMessage(), e.getFileName());
        }
        JSONObject response = new JSONObject();
        try {
            response.put("resMessage", resMessage);
            response.put("result", result);
            resMessage = response.toString();
        } catch (JSONException e) {
            resMessage = e.getMessage();
        }
        return resMessage;
    }

    @GetMapping(value = "/imageFile")
    public ResponseEntity<byte[]> getImageFile(@RequestParam(name="fileName") String fileName) {
        byte[] imageContext = null;
        HttpStatus status = HttpStatus.CREATED;
        try{
            imageContext = (new UploadFiles()).getImageFile(fileName);
        } catch (UploadExceptions e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        String ext = "";
        File f = new File(configProp.getConfigValue("directory.upload.files") + File.separator + configProp.getConfigValue("directory.upload.images") + File.separator + fileName);
        try {
            ext = Files.probeContentType(f.toPath());
        } catch (IOException e) {
            ext = "";
        }

        HttpHeaders headers = new HttpHeaders();
        if (ext.contains("jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        if (ext.contains("gif")) {
            headers.setContentType( MediaType.IMAGE_GIF);
        }
        if (ext.contains("png")) {
            headers.setContentType( MediaType.IMAGE_PNG);
        }
        return new ResponseEntity<byte[]>(imageContext, headers, status);
    }

    @GetMapping(value = "/videoFile")
    public ResponseEntity<byte[]> getVideoFile(@RequestParam(name="fileName") String fileName) {
        byte[] videoContext = null;
        HttpStatus status = HttpStatus.OK;
        try{
            videoContext = (new UploadFiles()).getVideoContext(fileName);
        } catch (UploadExceptions e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        File f = new File(configProp.getConfigValue("directory.upload.files") + File.separator + configProp.getConfigValue("directory.upload.images") + File.separator + fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(videoContext.length);
        return new ResponseEntity<byte[]>(videoContext, headers, status);
    }

    @GetMapping(value = "/videosrc", produces = "video/mp4")
    @ResponseBody
    public FileSystemResource videoSource(@RequestParam(value="fileName", required=true) String fileName) {
        try {
            File video = (new UploadFiles()).getVideoFile(fileName);
            return new FileSystemResource(video);
        } catch (UploadExceptions e){
          return null;
        }
    }

    @PostMapping("/deleteFile")
    public String deleteFile(@RequestParam("fileName") String fileName){
        Boolean result = false;
        String resMessage = null;

        UploadFiles.deleteImageFile(fileName);

        JSONObject json = new JSONObject();
        try{
            json.put("result", result);
            json.put("resMessage", resMessage);
            resMessage = json.toString();
        } catch (JSONException e){
        }
        return resMessage;
    }
}
