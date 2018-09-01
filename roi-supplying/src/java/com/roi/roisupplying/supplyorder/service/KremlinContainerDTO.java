
package com.roi.roisupplying.supplyorder.service;

import java.util.List;

public class KremlinContainerDTO {
    
    private String serviceName;
    private List<KremlinParamDTO> params;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<KremlinParamDTO> getParams() {
        return params;
    }

    public void setParams(List<KremlinParamDTO> params) {
        this.params = params;
    }
    
}
