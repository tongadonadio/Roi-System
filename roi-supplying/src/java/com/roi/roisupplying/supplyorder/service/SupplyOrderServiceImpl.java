package com.roi.roisupplying.supplyorder.service;

import com.roi.roisupplying.supplyorder.SupplyOrderDTO;
import com.roi.roisupplying.supplyorder.dao.SupplyOrderDAO;
import com.roi.roisupplying.supplyorder.SupplyOrderException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SupplyOrderServiceImpl implements SupplyOrderService {

    @EJB
    SupplyOrderDAO supplyOderDao;
    
    @Override
    public SupplyOrderDTO getOrder(Long orderNumber) throws SupplyOrderException {
        return supplyOderDao.findOrderByOrderNumber(orderNumber);
    }

    @Override
    public Long createOrder(SupplyOrderDTO order) throws SupplyOrderException {
        return supplyOderDao.save(order);
    }

    @Override
    public void updateOrder(SupplyOrderDTO order) throws SupplyOrderException {
        supplyOderDao.update(order);
    }

    @Override
    public void deleteOrder(Long orderNumber) throws SupplyOrderException {
        supplyOderDao.delete(orderNumber);
    }

}
