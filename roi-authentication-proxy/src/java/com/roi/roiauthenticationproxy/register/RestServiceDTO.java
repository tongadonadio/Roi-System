
package com.roi.roiauthenticationproxy.register;

import java.util.List;

public class RestServiceDTO {
    
    private String url;
    private String method;
    private List<ServiceItemDTO> queryParams;   
    private List<ServiceItemDTO> pathParams;
    private List<ServiceItemDTO> body;   
    private String name;
    private String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<ServiceItemDTO> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<ServiceItemDTO> queryParams) {
        this.queryParams = queryParams;
    }

    public List<ServiceItemDTO> getBody() {
        return body;
    }

    public void setBody(List<ServiceItemDTO> body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceItemDTO> getPathParams() {
        return pathParams;
    }

    public void setPathParams(List<ServiceItemDTO> pathParams) {
        this.pathParams = pathParams;
    }
    
}
