
package com.roi.roiauthenticationproxy.register;

import java.util.List;

public class JmsServiceDTO {
    
    private String host;
    private Integer port;
    private String name;
    private String queueName;
    private String description;
    private String connectionFactoryName;
    private List<ServiceItemDTO> bodyParams;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public List<ServiceItemDTO> getBodyParams() {
        return bodyParams;
    }

    public void setBodyParams(List<ServiceItemDTO> bodyParams) {
        this.bodyParams = bodyParams;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConnectionFactoryName() {
        return connectionFactoryName;
    }

    public void setConnectionFactoryName(String connectionFactoryName) {
        this.connectionFactoryName = connectionFactoryName;
    }
  
}
