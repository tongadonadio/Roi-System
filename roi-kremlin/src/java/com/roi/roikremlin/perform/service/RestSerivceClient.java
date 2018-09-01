package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.dao.RestMethod;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface RestSerivceClient {
        
    String callService(String url, RestMethod method, Map<String, String> queryParams, Map<String, Object> bodyParams, String token) throws RegisterServiceException;
    
}

