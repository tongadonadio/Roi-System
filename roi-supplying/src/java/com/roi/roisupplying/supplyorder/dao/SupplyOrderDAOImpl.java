package com.roi.roisupplying.supplyorder.dao;

import com.roi.roisupplying.supplyorder.MessageHelper;
import com.roi.roisupplying.supplyorder.RoiLogger;
import com.roi.roisupplying.supplyorder.SupplyOrderDTO;
import com.roi.roisupplying.supplyorder.SupplyOrderException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

@Stateless
public class SupplyOrderDAOImpl implements SupplyOrderDAO {

    @PersistenceContext(unitName = "roi-supplyingPU")
    private EntityManager em;

    @Inject
    private RoiLogger logger;
    
    @Inject
    private MessageHelper messages;
    
    @Override
    public Long save(SupplyOrderDTO supplyOrder) throws SupplyOrderException {
        SupplyOrder entity = SupplyOrderMapper.toEntity(supplyOrder);
        Long orderNumber = null;
        try {
            if(findByOrderNumber(entity.getOrderNumber()) == null) {
               em.persist(entity);
               orderNumber = entity.getOrderNumber();
            }
        } catch(IllegalArgumentException|TransactionRequiredException|EntityExistsException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyOrderException(messages.getMessage("supplyorder.save.error", supplyOrder.getOrderNumber().toString()));
        }
        return orderNumber;
    }
    
    @Override
    public void update(SupplyOrderDTO supplyOrder) throws SupplyOrderException {
        try {
            SupplyOrder entity = SupplyOrderMapper.toEntity(supplyOrder);
            if(findByOrderNumber(entity.getOrderNumber()) != null) {
               em.merge(entity);
            }
        } catch(IllegalArgumentException|TransactionRequiredException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyOrderException(messages.getMessage("supplyorder.update.error", supplyOrder.getOrderNumber().toString()));
        }
    }

    @Override
    public void delete(Long orderNumber) throws SupplyOrderException {
        try {
            SupplyOrder supplyOrder = findByOrderNumber(orderNumber);
            if(supplyOrder != null) {
                em.remove(supplyOrder);
            }
        } catch(IllegalArgumentException|TransactionRequiredException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyOrderException(messages.getMessage("supplyorder.delete.error", orderNumber.toString()));
        }
    }


    @Override
    public SupplyOrderDTO findOrderByOrderNumber(Long orderNumber) throws SupplyOrderException {
        try {
            SupplyOrder supplyOrder = findByOrderNumber(orderNumber);
            return supplyOrder != null ? SupplyOrderMapper.toDTO(supplyOrder) : null;
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new SupplyOrderException(messages.getMessage("supplyorder.find.error", orderNumber.toString()));
        }
    }
    
    private SupplyOrder findByOrderNumber(Long orderNumber){
        return em.find(SupplyOrder.class, orderNumber);
    }

}
