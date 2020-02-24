package ua.com.google.fediushyn.anton.consts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@Configuration("UploadProperies")
@ConfigurationProperties(prefix = "directory.upload")
public class UploadPropertiesImpl implements UploadProperties{
    @Value("images")
    private String pathImages;
    @Value("video")
    private String pathVideos;
    @Value("files")
    private String pathFiles;

    public UploadPropertiesImpl() {
    }

    public String getPathImages() {
        return pathImages;
    }

    public void setPathImages(String pathImages) {
        this.pathImages = pathImages;
    }

    public String getPathVideos() {
        return pathVideos;
    }

    public void setPathVideos(String pathVideos) {
        this.pathVideos = pathVideos;
    }

    public String getPathFiles() {
        return pathFiles;
    }

    public void setPathFiles(String pathFiles) {
        this.pathFiles = pathFiles;
    }
}
