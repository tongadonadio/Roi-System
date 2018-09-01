
package com.roi.roikremlin.registerservice;

import java.util.List;

public class ServiceEx {
    
    private String serviceName;
    private String serviceDescription;
    private List<ServiceParamsEx> params;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public List<ServiceParamsEx> getParams() {
        return params;
    }

    public void setParams(List<ServiceParamsEx> params) {
        this.params = params;
    }

}
