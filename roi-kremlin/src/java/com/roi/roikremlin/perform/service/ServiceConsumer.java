
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.perform.PerformRequestDTO;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import javax.ejb.Local;

@Local
public interface ServiceConsumer {
    
    String consumeService(PerformRequestDTO performRequest, RegisterServiceDTO registerServiceDTO) throws RegisterServiceException;
    
}
