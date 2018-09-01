
package com.roi.roikremlin.registerservice.dao;

import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.RegisteredApplicationEx;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RegisterServiceDAO {
    Long save(RegisterServiceDTO registerServiceDTO) throws RegisterServiceException;
    void delete(Long applicationId) throws RegisterServiceException;
    RegisteredApplicationEx findApplicationById(Long applicationId) throws RegisterServiceException;
    RegisterServiceDTO getRegisteredApplicationByServiceName(String serviceName) throws RegisterServiceException;
    List<RegisteredApplicationEx> getAll() throws RegisterServiceException;
}
