package com.roi.roisupplying.supplyorder;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

@Singleton
@LocalBean
public class MessageHelper {
    
    private ResourceBundle resourceBundle;
    
    @PostConstruct
    public void init() {
        resourceBundle = ResourceBundle.getBundle("messages");
    }

    public String getMessage(String key, String ... params) {
        return  getResoureMessage(key, params);
    }

    private String getResoureMessage(String key, String ... params) {
        String message = resourceBundle.getString(key); 
        if(ApplicationUtil.isNotNullAndEmpty(params)) {
            message = MessageFormat.format(message, (Object[])params);
        }
        return message;
    }
    
}
