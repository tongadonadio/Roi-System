
package com.roi.roiplanner.supplyplan.dao;

import com.roi.roiplanner.supplyplan.SupplyPlanDTO;
import com.roi.roiplanner.supplyplan.SupplyPlanException;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SupplyPlanDAO {
    
    SupplyPlanDTO getSupplyPlanById(Long id) throws SupplyPlanException;
    SupplyPlanDTO getSupplyPlanByOrderId(Long orderId) throws SupplyPlanException;
    List<SupplyPlanDTO> getAll() throws SupplyPlanException;
    void cancelSupplyPlan(Long id)throws SupplyPlanException;
    SupplyPlanDTO update(SupplyPlanDTO supplyPlan, Long id) throws SupplyPlanException;
    Long save(SupplyPlanDTO supplyPlan) throws SupplyPlanException;
    SupplyPlanDTO approve(Long id) throws SupplyPlanException;
    
}
