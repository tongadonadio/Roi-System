
package com.roi.roiplanner.supplyplan.service;

import com.roi.roiplanner.supplyplan.GsonUtil;
import com.roi.roiplanner.supplyplan.SettingsHelper;
import com.roi.roiplanner.supplyplan.SupplyPlanDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;

@Stateless
public class KremlinClient implements LogPlanClient {
    
    private static final String KREMLIN_URL = "roi.kremlin.url";
    
    @EJB
    private SettingsHelper settingsHelper;

    @Override
    public void logPlan(SupplyPlanDTO plan) {
        WebResource webResource = getWebResource();
        KremlinContainerDTO request = createKremlinRequest(plan);
        String json = GsonUtil.toJson(request);
        webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .post(ClientResponse.class, json);
    }
    
    private WebResource getWebResource() {
        Client client = Client.create();
        String url = settingsHelper.getProperty(KREMLIN_URL);
        WebResource webResource = client.resource(url);
        return webResource;
    }
    
    private KremlinContainerDTO createKremlinRequest(SupplyPlanDTO plan) {
        KremlinContainerDTO kremlinContainerDTO = new KremlinContainerDTO();
        
        kremlinContainerDTO.setServiceName("logPlan");
        List<KremlinParamDTO> params = new ArrayList<>();
        KremlinParamDTO kremlinParamDTO = new KremlinParamDTO();
        String json = GsonUtil.toJson(plan);
        
        kremlinParamDTO.setName("jsonBody");
        kremlinParamDTO.setValue(json);
        params.add(kremlinParamDTO);
        kremlinContainerDTO.setParams(params);
        
        return kremlinContainerDTO;
    }
    
}
