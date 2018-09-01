
package com.roi.roiplanner.supplyplan.service;

import com.roi.roiplanner.supplyplan.SupplyPlanDTO;
import javax.ejb.Local;

@Local
public interface LogPlanClient {
    
    void logPlan(SupplyPlanDTO plan);
    
}
