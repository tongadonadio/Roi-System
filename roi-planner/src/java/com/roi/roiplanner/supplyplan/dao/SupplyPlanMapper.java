
package com.roi.roiplanner.supplyplan.dao;

import com.roi.roiplanner.supplyplan.ApplicationUtil;
import com.roi.roiplanner.supplyplan.NetworkSectionDTO;
import com.roi.roiplanner.supplyplan.SupplyPlanDTO;
import java.util.List;
import java.util.stream.Collectors;


public class SupplyPlanMapper {
    
    public static SupplyPlanDTO toDTO(SupplyPlan supplyPlan) {
        SupplyPlanDTO supplyPlanDTO = new SupplyPlanDTO();
        
        supplyPlanDTO.setId(supplyPlan.getId());
        supplyPlanDTO.setOrderId(supplyPlan.getOrderId());
        supplyPlanDTO.setStatus(supplyPlan.getStatus().toString());
        
        if(ApplicationUtil.isNotNullAndEmpty(supplyPlan.getNetworkSections())) {
            List<NetworkSectionDTO> networkSections = supplyPlan.getNetworkSections()
                    .stream()
                    .map(ns -> mapNetworkSectionDTO(ns))
                    .collect(Collectors.toList());
        
                supplyPlanDTO.setNetworkSections(networkSections);
        }
               
        return supplyPlanDTO;
    }
    
    private static NetworkSectionDTO mapNetworkSectionDTO(NetworkSection networkSection) {
        NetworkSectionDTO networkSectionDTO = new NetworkSectionDTO();
        
        networkSectionDTO.setActuatorId(networkSection.getActuatorId());
        networkSectionDTO.setSectionId(networkSection.getSectionId());
        networkSectionDTO.setSourceId(networkSection.getSourceId());
        
        return networkSectionDTO;
    }
    
    public static SupplyPlan toEntity(SupplyPlanDTO supplyPlanDTO) {
        SupplyPlan supplyPlan = new SupplyPlan();
        
        supplyPlan.setOrderId(supplyPlanDTO.getOrderId());
        supplyPlan.setStatus(SupplyPlanStatus.valueOf(supplyPlanDTO.getStatus()));
        
        if(ApplicationUtil.isNotNullAndEmpty(supplyPlanDTO.getNetworkSections())) {
            List<NetworkSection> networkSections = supplyPlanDTO.getNetworkSections()
                    .stream()
                    .map(ns -> mapNetworkSectionEntity(ns, supplyPlan))
                    .collect(Collectors.toList());
        
                supplyPlan.setNetworkSections(networkSections);
        }        
        
        return supplyPlan;
    }
    
    private static NetworkSection mapNetworkSectionEntity(NetworkSectionDTO networkSectionDTO, SupplyPlan supplyPlan) {
        NetworkSection networkSection = new NetworkSection();
        
        networkSection.setActuatorId(networkSectionDTO.getActuatorId());
        networkSection.setSectionId(networkSectionDTO.getSectionId());
        networkSection.setSourceId(networkSectionDTO.getSourceId());
        networkSection.setSupplyPlan(supplyPlan);
        
        return networkSection;
    }
    
}
