package com.roi.roisupplying.supplyorder.service;

import com.roi.roisupplying.supplyorder.SupplyOrderDTO;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SupplyOrderValidationService {
    public List<String> validateOrder(SupplyOrderDTO supplyOrder);
}
