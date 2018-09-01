package com.roi.goliath.planapproval;

import java.util.ArrayList;

public class SupplyPlanDTO {
    private Long id;
    private ArrayList<NetworkSectionDTO> networkSections;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<NetworkSectionDTO> getNetworkSections() {
        return networkSections;
    }

    public void setNetworkSections(ArrayList<NetworkSectionDTO> networkSections) {
        this.networkSections = networkSections;
    }
}
