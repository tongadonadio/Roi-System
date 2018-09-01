
package com.roi.roiplanner.supplyplan.service;

import com.roi.roiplanner.supplyplan.NetworkSectionDTO;
import java.util.List;
import javax.ejb.Local;

@Local
public interface NetworkSectionClient {
    
    List<NetworkSectionDTO> getNetworkSections(Long supplyPlanId);
    
}
