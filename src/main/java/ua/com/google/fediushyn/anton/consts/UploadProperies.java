package ua.com.google.fediushyn.anton.consts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@Configuration("UploadProperies")
@ConfigurationProperties(prefix = "directory.upload")
public class UploadProperies {
    @Value("images")
    private String images;
    @Value("video")
    private String video;
    @Value("files")
    private String files;

    public UploadProperies() {
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
