
package com.roi.roikremlin.perform.service;

import java.util.Map;
import javax.ejb.Local;

@Local
public interface JmsServiceClient {
    
    String callService(String host, Integer port, String queueName, String connectionFactoryName, Map<String, Object> bodyParams);
    
}
