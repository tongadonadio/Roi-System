
package com.roi.roikremlin.registerservice.service;

import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.RegisteredApplicationEx;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RegisterApplicationService {
    
    Long registerApplication(RegisterServiceDTO registerService) throws RegisterServiceException;
    RegisteredApplicationEx getRegisteredApplicationById(Long id) throws RegisterServiceException;
    void unregisterApplicationById(Long id) throws RegisterServiceException;
    List<RegisteredApplicationEx> getAllRegisteredApplications() throws RegisterServiceException;
    
}
