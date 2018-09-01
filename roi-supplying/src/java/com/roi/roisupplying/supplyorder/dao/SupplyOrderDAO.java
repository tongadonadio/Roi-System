package com.roi.roisupplying.supplyorder.dao;

import com.roi.roisupplying.supplyorder.SupplyOrderDTO;
import com.roi.roisupplying.supplyorder.SupplyOrderException;
import javax.ejb.Local;

@Local
public interface SupplyOrderDAO {
    Long save(SupplyOrderDTO supplyOrder) throws SupplyOrderException;
    void update(SupplyOrderDTO supplyOrder) throws SupplyOrderException;
    void delete(Long orderNumber) throws SupplyOrderException;
    SupplyOrderDTO findOrderByOrderNumber(Long orderNumber) throws SupplyOrderException;
}
