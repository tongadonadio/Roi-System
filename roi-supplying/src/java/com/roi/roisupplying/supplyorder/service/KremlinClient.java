
package com.roi.roisupplying.supplyorder.service;

import com.roi.roisupplying.supplyorder.GsonUtil;
import com.roi.roisupplying.supplyorder.SettingsHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;

@Stateless
public class KremlinClient implements SupplyPlanClient {
    
    private static final String KREMLIN_URL = "roi.kremlin.url";
    
    @EJB
    private SettingsHelper settingsHelper;

    @Override
    public void createSupplyPlan(Long orderId) {
        WebResource webResource = getWebResource();
        KremlinContainerDTO request = createKremlinRequest(orderId);
        String json = GsonUtil.toJson(request);
        webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .post(ClientResponse.class, json);
    }

    @Override
    public void modifyOrderPlan(Long orderId) {
        WebResource webResource = getWebResource();
        KremlinContainerDTO request = createKremlinRequest(orderId);
        String json = GsonUtil.toJson(request);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .post(ClientResponse.class, json);
        
        KremlinPlanDTO plan = response.getEntity(KremlinPlanDTO.class);
        
        KremlinContainerDTO modifyRequest = modifyKremlinRequest(plan.getId());
        json = GsonUtil.toJson(modifyRequest);
        webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .post(ClientResponse.class, json);
    }

    @Override
    public void deleteOrderPlan(Long orderId) {
        WebResource webResource = getWebResource();
        KremlinContainerDTO request = createKremlinRequest(orderId);
        String json = GsonUtil.toJson(request);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .post(ClientResponse.class, json);
        
        KremlinPlanDTO plan = response.getEntity(KremlinPlanDTO.class);
        
        KremlinContainerDTO cancelRequest = removeKremlinRequest(plan.getId());
        json = GsonUtil.toJson(cancelRequest);
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
    
    private KremlinContainerDTO modifyKremlinRequest(String planId) {
        KremlinContainerDTO kremlinContainerDTO = new KremlinContainerDTO();
        
        kremlinContainerDTO.setServiceName("modifyPlan");
        List<KremlinParamDTO> params = new ArrayList<>();
        KremlinParamDTO kremlinParamDTO = new KremlinParamDTO();
        kremlinParamDTO.setName("planId");
        kremlinParamDTO.setValue(planId);
        params.add(kremlinParamDTO);
        kremlinContainerDTO.setParams(params);
        
        return kremlinContainerDTO;
    }
    
    private KremlinContainerDTO removeKremlinRequest(String planId) {
        KremlinContainerDTO kremlinContainerDTO = new KremlinContainerDTO();
        
        kremlinContainerDTO.setServiceName("cancelPlanById");
        List<KremlinParamDTO> params = new ArrayList<>();
        KremlinParamDTO kremlinParamDTO = new KremlinParamDTO();
        kremlinParamDTO.setName("planId");
        kremlinParamDTO.setValue(planId);
        params.add(kremlinParamDTO);
        kremlinContainerDTO.setParams(params);
        
        return kremlinContainerDTO;
    }
    
    private KremlinContainerDTO createKremlinRequest(Long orderId) {
        KremlinContainerDTO kremlinContainerDTO = new KremlinContainerDTO();
        
        kremlinContainerDTO.setServiceName("createPlan");
        List<KremlinParamDTO> params = new ArrayList<>();
        KremlinParamDTO kremlinParamDTO = new KremlinParamDTO();
        kremlinParamDTO.setName("orderId");
        kremlinParamDTO.setValue(orderId.toString());
        params.add(kremlinParamDTO);
        kremlinContainerDTO.setParams(params);
        
        return kremlinContainerDTO;
    }
    
    private KremlinContainerDTO getKremlinPlanRequest(Long orderId) {
        KremlinContainerDTO kremlinContainerDTO = new KremlinContainerDTO();
        
        kremlinContainerDTO.setServiceName("getPlanByOrderId");
        List<KremlinParamDTO> params = new ArrayList<>();
        KremlinParamDTO kremlinParamDTO = new KremlinParamDTO();
        kremlinParamDTO.setName("orderId");
        kremlinParamDTO.setValue(orderId.toString());
        params.add(kremlinParamDTO);
        kremlinContainerDTO.setParams(params);
        
        return kremlinContainerDTO;
    }
    
}
