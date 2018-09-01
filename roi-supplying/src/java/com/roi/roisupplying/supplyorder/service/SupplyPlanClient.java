
package com.roi.roisupplying.supplyorder.service;

import javax.ejb.Local;

@Local
public interface SupplyPlanClient {
    
    void createSupplyPlan(Long orderId);
    void modifyOrderPlan(Long orderId);
    void deleteOrderPlan(Long orderId);
    
}
