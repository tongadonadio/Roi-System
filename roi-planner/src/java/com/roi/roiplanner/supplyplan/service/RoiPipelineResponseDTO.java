
package com.roi.roiplanner.supplyplan.service;

import java.util.List;

public class RoiPipelineResponseDTO {
    
    private List<RoiPipelineNetworkSectionDTO> sections;

    public List<RoiPipelineNetworkSectionDTO> getSections() {
        return sections;
    }

    public void setSections(List<RoiPipelineNetworkSectionDTO> sections) {
        this.sections = sections;
    }
    
}
