
package com.roi.roikremlin.registerservice.service;

import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.RegisteredApplicationEx;
import com.roi.roikremlin.registerservice.dao.RegisterServiceDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class RegisterApplicationServiceImpl implements RegisterApplicationService {
    
    @EJB
    private RegisterServiceDAO registerServiceDAO;

    @Override
    public Long registerApplication(RegisterServiceDTO registerService) throws RegisterServiceException {
        return this.registerServiceDAO.save(registerService);
    }

    @Override
    public RegisteredApplicationEx getRegisteredApplicationById(Long id) throws RegisterServiceException {
        return this.registerServiceDAO.findApplicationById(id);
    }

    @Override
    public void unregisterApplicationById(Long id) throws RegisterServiceException {
        this.registerServiceDAO.delete(id);
    }

    @Override
    public List<RegisteredApplicationEx> getAllRegisteredApplications() throws RegisterServiceException {
        return this.registerServiceDAO.getAll();
    }
    
}
