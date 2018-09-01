
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.perform.PerformRequestDTO;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.dao.RegisterServiceDAO;
import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class PerformRequestServiceImpl implements PerformRequestService {

    @EJB
    private RegisterServiceDAO registerServiceDAO;
    
    @EJB
    private ServiceConsumerDirector serviceConsumerDirector;
    
    @Override
    public String performRequest(PerformRequestDTO performRequest) throws RegisterServiceException {
       RegisterServiceDTO registerServiceDTO = this.registerServiceDAO.getRegisteredApplicationByServiceName(performRequest.getServiceName());
       
       ServiceConsumer serviceConsumer = serviceConsumerDirector.getServiceConsumer(registerServiceDTO.getType());
       return serviceConsumer.consumeService(performRequest, registerServiceDTO);
    }
    
}