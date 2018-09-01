package com.roi.roikremlin.registerservice;

import java.util.List;

public class RegisterServiceDTO {
    
    private Long id;
    
    private String type;
    
    private List<RestServiceDTO> restServices; 
    
    private List<JmsServiceDTO> jmsServices;
    
    private String token;
    
    private String applicationName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public List<RestServiceDTO> getRestServices() {
        return restServices;
    }

    public void setRestServices(List<RestServiceDTO> restServices) {
        this.restServices = restServices;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JmsServiceDTO> getJmsServices() {
        return jmsServices;
    }

    public void setJmsServices(List<JmsServiceDTO> jmsServices) {
        this.jmsServices = jmsServices;
    }

}
