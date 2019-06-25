package ua.com.google.fediushyn.anton.upload;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import ua.com.google.fediushyn.anton.props.ConfigProperties;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UploadFiles implements UploadFile {
    private final static long maxImageFileSize = 100000000;
    private final static String FMT_IMAGE_NAME = "image_%s.%s";
    private final static String FMT_IMAGE_NAME_ARR = "image_%d_%s.%s";
    private final static String FMT_POSTER_NAME = "poster_%s.%s";

    public String uploadSingleImageFile(MultipartFile file, Boolean isPoser) throws UploadExceptions {
        String fileName;
        if (file.isEmpty()) {
            throw new UploadExceptions("Empty.uploadForm.file",
                    "Failed to upload empty file %s!", file.getOriginalFilename());
        }

        String contentType = file.getContentType();
        if ((contentType != null) && (!contentType.contains("image"))) {
            throw new UploadExceptions("NoImage.uploadForm.file",
                    "The file %s is not an image!!", file.getOriginalFilename());
        }
        if (file.getSize() > UploadFiles.maxImageFileSize) {
            throw new UploadExceptions("Size.uploadForm.file",
                    "File %s size exceeds maximum size allowed!", file.getOriginalFilename());
        }
        try {
            if (isPoser) {
                fileName = String.format(FMT_POSTER_NAME, new SimpleDateFormat("yyyymmddhhmmss").format(new Date()),
                    FilenameUtils.getExtension(file.getOriginalFilename()));
            } else {
                fileName = String.format(FMT_IMAGE_NAME, new SimpleDateFormat("yyyymmddhhmmss").format(new Date()),
                        FilenameUtils.getExtension(file.getOriginalFilename()));
            }

            File dir = new File(ConfigProperties.getPropertyValue("directory.upload.files") + File.separator + ConfigProperties.getPropertyValue("directory.upload.images"));

            if (!dir.exists()){
                if (!dir.mkdirs()) {
                    throw new UploadExceptions("Filed.create.directory", "Filed to create directory!");
                }
            }

            File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);

            file.transferTo(uploadedFile);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UploadExceptions("Filed.uploadForm.file",
                    "Failed to upload file %s !", e, file.getOriginalFilename());
        }
    }

    public List<String> uploadMultiImageFiles(MultipartFile[] files) throws UploadExceptions {
        List<String> filesName = new ArrayList<>();
        if (files.length == 0) {
            throw new UploadExceptions("Length.uploadForm.files",
                    "The number of files to download is 0!");
        }
        int pos = 1;
        for (MultipartFile file: files) {
            try {
                if (file.isEmpty()) {
                    throw new UploadExceptions("Empty.uploadForm.file",
                            "Failed to upload empty file %s!", file.getOriginalFilename());
                }

                if (file.getSize() > UploadFiles.maxImageFileSize) {
                    throw new UploadExceptions("Size.uploadForm.file",
                            "File %s size exceeds maximum size allowed!", file.getOriginalFilename());
                }

                String fileName = String.format(FMT_IMAGE_NAME_ARR, pos++, new SimpleDateFormat("yyyymmddhhmmss").format(new Date()),
                        FilenameUtils.getExtension(file.getOriginalFilename()));

                File dir = new File(ConfigProperties.getPropertyValue("directory.upload.files") + File.separator + ConfigProperties.getPropertyValue("directory.upload.images"));

                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        throw new UploadExceptions("Filed.create.directory", "Filed to create directory!");
                    }
                }

                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);

                file.transferTo(uploadedFile);
                filesName.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                throw new UploadExceptions("Filed.uploadForm.file",
                        "Failed to upload file %s !", e, file.getOriginalFilename());
            }
        }
        return filesName;
    }

    public String uploadVideoFile(MultipartFile file) throws UploadExceptions{
        String fileName;
        try {
            if (file.isEmpty()) {
                throw new UploadExceptions("Empty.uploadForm.file",
                        "Failed to upload empty file %s", file.getOriginalFilename());
            }

            fileName = UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(file.getOriginalFilename());


            File dir = new File(ConfigProperties.getPropertyValue("directory.upload.files") + File.separator + ConfigProperties.getPropertyValue("directory.upload.video"));

            if (!dir.exists()){
                if (!dir.mkdirs()) {
                    throw new UploadExceptions("Filed.create.directory", "Filed to create directory!");
                }
            }

            File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);

            file.transferTo(uploadedFile);

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UploadExceptions("Filed.uploadForm.file",
                    "Failed to upload file %s!", e, file.getOriginalFilename());
        }
    }

    public byte[] getImageFile(String fileName) throws UploadExceptions{
        byte[] imageContext;
        File dir = new File(ConfigProperties.getPropertyValue("directory.upload.files") + File.separator + ConfigProperties.getPropertyValue("directory.upload.images"));
        if (!dir.exists()) {
            throw new UploadExceptions("Directory.images.not.exists", "Images directory not found!");
        }
        File loadFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        if (!loadFile.exists()) {
            throw new UploadExceptions("File.image.not.found", "Image %s not found!", fileName);
        }
        try {
            imageContext = Files.readAllBytes(loadFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new UploadExceptions("Filed.load.image.file", "Filed to load image!");
        }
        return imageContext;
    }

    public File getVideoFile(String fileName) throws UploadExceptions{
        File video;
        File dir = new File(ConfigProperties.getPropertyValue("directory.upload.files") + File.separator + ConfigProperties.getPropertyValue("directory.upload.video"));
        if (!dir.exists()) {
            throw new UploadExceptions("Directory.videos.not.exists", "Videos directory not found!");
        }
        video = new File(dir.getAbsolutePath() + File.separator + fileName);
        if (!video.exists()) {
            throw new UploadExceptions("File.video.not.found", "Video %s not found!", fileName);
        }
        return video;
    }

    public byte[] getVideoContext(String fileName) throws UploadExceptions{
        byte[] videoContext;
        File dir = new File(ConfigProperties.getPropertyValue("directory.upload.files") + File.separator + ConfigProperties.getPropertyValue("directory.upload.video"));
        if (!dir.exists()) {
            throw new UploadExceptions("Directory.videos.not.exists", "Videos directory not found!");
        }
        File loadFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        if (!loadFile.exists()) {
            throw new UploadExceptions("File.video.not.found", "Video %s not found!", fileName);
        }

        try{
            videoContext = Files.readAllBytes(loadFile.toPath());
        } catch (IOException e){
            e.printStackTrace();
            throw  new UploadExceptions("Filed.load.video.file", "Filed to load video!");
        }
        return videoContext;
    }

    public static void deleteImageFile(String fileName){
        File dir = new File(ConfigProperties.getPropertyValue("directory.upload.files") + File.separator + ConfigProperties.getPropertyValue("directory.upload.images"));
        if (!dir.exists()) {
            return;
        }
        File deleteFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        if (!deleteFile.exists()) {
            return;
        }

        deleteFile.delete();
    }

    public static void deleteVideoFile(String fileName){
        File dir = new File(ConfigProperties.getPropertyValue("directory.upload.files") + File.separator + ConfigProperties.getPropertyValue("directory.upload.video"));
        if (!dir.exists()) {
            return;
        }
        File deleteFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        if (!deleteFile.exists()) {
            return;
        }

        deleteFile.delete();
    }
}
