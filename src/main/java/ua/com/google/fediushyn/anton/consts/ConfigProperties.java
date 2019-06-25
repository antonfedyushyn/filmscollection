package ua.com.google.fediushyn.anton.consts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    private final Environment env;

    @Autowired
    ConfigProperties(Environment env){
        super();
        this.env = env;
    }

    public String getConfigValue(String configKey){
        return env.getProperty(configKey);
    }
}
