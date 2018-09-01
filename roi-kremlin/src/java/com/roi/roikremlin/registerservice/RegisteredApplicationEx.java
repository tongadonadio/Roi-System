
package com.roi.roikremlin.registerservice;

import java.util.List;

public class RegisteredApplicationEx {
    
    private Long id;
    private String applicationName;
    private List<ServiceEx> services;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<ServiceEx> getServices() {
        return services;
    }

    public void setServices(List<ServiceEx> services) {
        this.services = services;
    }

}
