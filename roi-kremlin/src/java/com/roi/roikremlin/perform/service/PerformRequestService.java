
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.perform.PerformRequestDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import javax.ejb.Local;

@Local
public interface PerformRequestService {
    
    String performRequest(PerformRequestDTO performRequest) throws RegisterServiceException;
    
}
