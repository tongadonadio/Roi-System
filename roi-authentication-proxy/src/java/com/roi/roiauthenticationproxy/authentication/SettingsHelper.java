package com.roi.roiauthenticationproxy.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
@LocalBean
public class SettingsHelper {
        
    private static final String SETTINGS_FILE_NAME = "settings.properties";
    
    @Inject
    private RoiLogger logger;
    
    private Properties properties;
    
    @PostConstruct
    public void init() {
        properties = new Properties();
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(SETTINGS_FILE_NAME);
            if(input != null) {
                properties.load(input);
            }
        }catch(IOException e) {
            logger.error("Error while trying to load properties file", e, SettingsHelper.class);
        }
    }
    
    public String getProperty(String key) {
        return properties != null ? properties.getProperty(key) : null;
    }
}
