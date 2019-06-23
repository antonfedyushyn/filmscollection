package ua.com.google.fediushyn.anton.props;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    public static String getPropertyValue(String propertyKey, String defaultValue) {
        FileInputStream fis;
        Properties property = new Properties();
        String value = "";
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
            value = property.getProperty(propertyKey, defaultValue);
        } catch (IOException e) {

        }
        return value;
    }

    public static String getPropertyValue(String propertyKey) {
        return getPropertyValue(propertyKey, "");
    }


}
