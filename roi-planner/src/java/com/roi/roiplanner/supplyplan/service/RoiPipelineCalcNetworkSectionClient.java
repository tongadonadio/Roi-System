
package com.roi.roiplanner.supplyplan.service;

import com.roi.roiplanner.supplyplan.NetworkSectionDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;

@Stateless
public class RoiPipelineCalcNetworkSectionClient implements NetworkSectionClient {

    private static final String ROI_PIPE_CALC_URL = "https://pipeline-calculator-api.herokuapp.com/pipeline-route/service/";
    
    @Override
    public List<NetworkSectionDTO> getNetworkSections(Long supplyPlanId) {
        List<NetworkSectionDTO> networkSections = null;
        Client client = Client.create();
        String url = ROI_PIPE_CALC_URL + supplyPlanId.toString();
        WebResource webResource = client.resource(url);
        
        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);
        RoiPipelineResponseDTO roiPipielineResponse = clientResponse.getEntity(RoiPipelineResponseDTO.class);
        
        if(roiPipielineResponse != null && roiPipielineResponse.getSections() != null) {
            if(roiPipielineResponse.getSections().size() > 0) {
                networkSections = roiPipielineResponse.getSections()
                        .stream()
                        .map(sec -> createNetworkSection(sec))
                        .collect(Collectors.toList());
            }
        }
        
        return networkSections;
    }
    
    private NetworkSectionDTO createNetworkSection(RoiPipelineNetworkSectionDTO roiSection) {
        NetworkSectionDTO networkSection = new NetworkSectionDTO();
        
        networkSection.setActuatorId(roiSection.getActuatorId());
        networkSection.setSectionId(roiSection.getSectionId());
        networkSection.setSourceId(roiSection.getSourceId());
        
        return networkSection;
    }
    
}
