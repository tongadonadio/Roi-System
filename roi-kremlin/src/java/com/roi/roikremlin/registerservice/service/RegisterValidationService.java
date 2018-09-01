
package com.roi.roikremlin.registerservice.service;

import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RegisterValidationService {
    
    List<String> validate(RegisterServiceDTO registerService) throws RegisterServiceException;
    
}
