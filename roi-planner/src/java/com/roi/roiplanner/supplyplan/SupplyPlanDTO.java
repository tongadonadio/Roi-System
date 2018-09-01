
package com.roi.roiplanner.supplyplan;

import java.util.List;

public class SupplyPlanDTO {
    
    private Long id;
    private String status;
    private List<NetworkSectionDTO> networkSections;
    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NetworkSectionDTO> getNetworkSections() {
        return networkSections;
    }

    public void setNetworkSections(List<NetworkSectionDTO> networkSections) {
        this.networkSections = networkSections;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
}
