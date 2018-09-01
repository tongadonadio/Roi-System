
package com.roi.roiplanner.supplyplan.dao;

import com.roi.roiplanner.supplyplan.ApplicationUtil;
import com.roi.roiplanner.supplyplan.MessageHelper;
import com.roi.roiplanner.supplyplan.RoiLogger;
import com.roi.roiplanner.supplyplan.SupplyPlanDTO;
import com.roi.roiplanner.supplyplan.SupplyPlanException;
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
public class SupplyPlanDAOImpl implements SupplyPlanDAO {
    
    @PersistenceContext(unitName = "roi-plannerPU")
    private EntityManager em;
    
    @EJB
    private RoiLogger logger;
    
    @Inject
    private MessageHelper messages;
    
    @Override
    public SupplyPlanDTO getSupplyPlanById(Long id) throws SupplyPlanException {
        try {
            SupplyPlan entity = findSupplyPlanEntityById(id);
            return entity != null ? SupplyPlanMapper.toDTO(entity) : null;
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyPlanException(messages.getMessage("supplyplan.find.error", id.toString()));
        }
    }

    @Override
    public List<SupplyPlanDTO> getAll() throws SupplyPlanException {
        try {
            TypedQuery<SupplyPlan> query = em.createQuery("from SupplyPlan s", SupplyPlan.class);
            
            return query.getResultList()
                    .stream()
                    .map(sp -> SupplyPlanMapper.toDTO(sp))
                    .collect(Collectors.toList());
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyPlanException(messages.getMessage("supplyplan.find.all.error"));
        }
    }

    @Override
    public void cancelSupplyPlan(Long id) throws SupplyPlanException {
        try {
            SupplyPlan entity = this.findSupplyPlanEntityById(id);
            entity.setId(id);
            entity.setStatus(SupplyPlanStatus.CANCELED);
            em.merge(entity);
        } catch(IllegalArgumentException|TransactionRequiredException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyPlanException(messages.getMessage("supplyplan.delete.error", id.toString()));
        }
    }

    @Override
    public SupplyPlanDTO update(SupplyPlanDTO supplyPlan, Long id) throws SupplyPlanException {
        try {
            SupplyPlan entity = SupplyPlanMapper.toEntity(supplyPlan);
            supplyPlan.setId(id);
            entity.setId(id);
            if(findSupplyPlanEntityById(id) != null) {
               em.merge(entity);
            }
            return supplyPlan;
        } catch(IllegalArgumentException|TransactionRequiredException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyPlanException(messages.getMessage("supplyplan.update.error", id.toString()));
        }
    }

    @Override
    public Long save(SupplyPlanDTO supplyPlan) throws SupplyPlanException {
        SupplyPlan entity = SupplyPlanMapper.toEntity(supplyPlan);
        Long id = null;
        try {
            em.persist(entity);
            em.flush();
            id = entity.getId();
        } catch(IllegalArgumentException|TransactionRequiredException|EntityExistsException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyPlanException(messages.getMessage("supplyplan.save.error"));
        }
        return id;
    }
    
    @Override
    public SupplyPlanDTO getSupplyPlanByOrderId(Long orderId) throws SupplyPlanException {
        try {
            TypedQuery<SupplyPlan> query = em.createQuery("from SupplyPlan s where s.orderId = :orderId", SupplyPlan.class);
            query.setParameter("orderId", orderId);
            List<SupplyPlan> sp = query.getResultList();
            SupplyPlan entity = null;
            
            if(ApplicationUtil.isNotNullAndEmpty(sp)) {
                entity = sp.get(0);
            }
            
            return entity != null ? SupplyPlanMapper.toDTO(entity) : null;
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyPlanException(messages.getMessage("supplyplan.find.error", "orderId" + orderId.toString()));
        }
    }
    
    private SupplyPlan findSupplyPlanEntityById(Long id){
        return em.find(SupplyPlan.class, id);
    }

    @Override
    public SupplyPlanDTO approve(Long id) throws SupplyPlanException {
        try {
            SupplyPlan entity = this.findSupplyPlanEntityById(id);
            entity.setId(id);
            entity.setStatus(SupplyPlanStatus.APPROVED);
            em.merge(entity);
            return SupplyPlanMapper.toDTO(entity);
        } catch(IllegalArgumentException|TransactionRequiredException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyPlanException(messages.getMessage("supplyplan.find.error", id.toString()));
        }
    }
    
}
