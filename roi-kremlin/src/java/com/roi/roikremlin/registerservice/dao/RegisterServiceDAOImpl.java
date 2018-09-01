
package com.roi.roikremlin.registerservice.dao;

import com.roi.roikremlin.registerservice.ApplicationUtil;
import com.roi.roikremlin.registerservice.MessageHelper;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.RegisteredApplicationEx;
import com.roi.roikremlin.registerservice.RoiLogger;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

@Stateless
public class RegisterServiceDAOImpl implements RegisterServiceDAO {
    
    @PersistenceContext(unitName = "roi-kremlinPU")
    private EntityManager em;
    
    @EJB
    private RoiLogger logger;
    
    @Inject
    private MessageHelper messages;

    @Override
    public Long save(RegisterServiceDTO registerServiceDTO) throws RegisterServiceException {
        RegisteredApplication entity = RegisteredApplicationMapper.toEntity(registerServiceDTO);
        Long applicationId = null;
        try {
            if(findApplicationEntityByName(entity.getApplicationName()) == null) {
                em.persist(entity);
                em.flush();
                applicationId = entity.getId();
            } else {
                throw new RegisterServiceException(messages.getMessage("registerservice.save.application.name.error", entity.getApplicationName()));
            }
        } catch(IllegalArgumentException|TransactionRequiredException|EntityExistsException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new RegisterServiceException(messages.getMessage("registerservice.save.error", entity.getApplicationName()));
        }
        return applicationId;
    }

    @Override
    public void delete(Long applicationId) throws RegisterServiceException {
        try {
            RegisteredApplication entity = findApplicationEntityById(applicationId);
            if(entity != null) {
                em.remove(entity);
            }
        } catch(IllegalArgumentException|TransactionRequiredException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new RegisterServiceException(messages.getMessage("registerservice.delete.error", applicationId.toString()));
        }
    }

    @Override
    public RegisteredApplicationEx findApplicationById(Long applicationId) throws RegisterServiceException {
        try {
            RegisteredApplication entity = findApplicationEntityById(applicationId);
            return entity != null ? RegisteredApplicationMapper.toExchange(entity) : null;
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new RegisterServiceException(messages.getMessage("registerservice.find.error", applicationId.toString()));
        }
    }

    @Override
    public List<RegisteredApplicationEx> getAll() throws RegisterServiceException {
        try {
            TypedQuery<RegisteredApplication> query = em.createQuery("from RegisteredApplication r", RegisteredApplication.class);
            return query.getResultList()
                    .stream()
                    .map(ra -> RegisteredApplicationMapper.toExchange(ra))
                    .collect(Collectors.toList());
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new RegisterServiceException(messages.getMessage("registerservice.find.all.error"));
        }
    }
    
    @Override
    public RegisterServiceDTO getRegisteredApplicationByServiceName(String serviceName) throws RegisterServiceException {
        TypedQuery<RestService> query = em.createQuery("from RestService r where r.name = :name", RestService.class);
        query.setParameter("name", serviceName);
        List<RestService> restServices = query.getResultList();
        
        TypedQuery<JmsService> jmsQuery = em.createQuery("from JmsService j where j.name = :name", JmsService.class);
        jmsQuery.setParameter("name", serviceName);
        List<JmsService> jmsServices = jmsQuery.getResultList();
        
        RegisterServiceDTO registerService = null;
        if(ApplicationUtil.isNotNullAndEmpty(restServices)) {
            registerService = RegisteredApplicationMapper.toDTO(restServices.get(0).getRegisteredApplication());
        }
        if(ApplicationUtil.isNotNullAndEmpty(jmsServices)) {
            registerService = RegisteredApplicationMapper.toDTO(jmsServices.get(0).getRegisteredApplication());
        }
        
        return registerService;
    }
        
    private RegisteredApplication findApplicationEntityById(Long applicationId){
        return em.find(RegisteredApplication.class, applicationId);
    }
    
    private RegisteredApplication findApplicationEntityByName(String applicationName){
        TypedQuery<RegisteredApplication> query = em.createQuery("from RegisteredApplication r where r.applicationName = :applicationName", RegisteredApplication.class);
        query.setParameter("applicationName", applicationName);
        List<RegisteredApplication> applications = query.getResultList();
        
        return ApplicationUtil.isNotNullAndEmpty(applications) ? applications.get(0) : null;
    }
    
}
