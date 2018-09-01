package com.roi.roisupplying.supplyorder.service;

import com.roi.roisupplying.supplyorder.SupplyOrderDTO;
import com.roi.roisupplying.supplyorder.SupplyOrderException;
import javax.ejb.Local;

@Local
public interface SupplyOrderService {
    SupplyOrderDTO getOrder(Long orderNumber) throws SupplyOrderException;
    Long createOrder(SupplyOrderDTO order) throws SupplyOrderException;
    void updateOrder(SupplyOrderDTO order) throws SupplyOrderException;
    void deleteOrder(Long orderNumber) throws SupplyOrderException;
}
