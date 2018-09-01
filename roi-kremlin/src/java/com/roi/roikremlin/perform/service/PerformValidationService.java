
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.perform.PerformRequestDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PerformValidationService {
    
    List<String> validate(PerformRequestDTO performRequest) throws RegisterServiceException;
    
}
