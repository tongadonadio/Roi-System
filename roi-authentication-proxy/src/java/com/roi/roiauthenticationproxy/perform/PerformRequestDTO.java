
package com.roi.roiauthenticationproxy.perform;

import java.util.List;

public class PerformRequestDTO {
    
    private String serviceName;
    
    private List<PerformParamDTO> params;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<PerformParamDTO> getParams() {
        return params;
    }

    public void setParams(List<PerformParamDTO> params) {
        this.params = params;
    }
}
