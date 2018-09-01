
package com.roi.roiplanner.supplyplan.service;

import com.roi.roiplanner.supplyplan.SupplyPlanDTO;
import com.roi.roiplanner.supplyplan.SupplyPlanException;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SupplyPlanService {
    
    SupplyPlanDTO getSupplyPlanById(Long supplyPlanId) throws SupplyPlanException;
    SupplyPlanDTO getSupplyPlanByOrderId(Long orderId) throws SupplyPlanException;
    List<SupplyPlanDTO> getAllSupplyPlans() throws SupplyPlanException;
    void cancelSupplyPlan(Long supplyPlanId)throws SupplyPlanException;
    SupplyPlanDTO modifySupplyPlan(Long supplyPlanId) throws SupplyPlanException;
    Long createSupplyPlan(Long orderId) throws SupplyPlanException;
    void approvePlan(Long supplyPlanId) throws SupplyPlanException;

}
