
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.registerservice.ApplicationUtil;
import com.roi.roikremlin.registerservice.GsonUtil;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.dao.RestMethod;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

@Stateless
public class RestServiceClientImpl implements RestSerivceClient {
    
    public String callService(String url, RestMethod method, Map<String, String> queryParams, Map<String, Object> bodyParams, String token) throws RegisterServiceException {
        String jsonResponse = null;
        Client client = Client.create();
        ClientResponse response = null;

        WebResource webResource = client.resource(url);
        MultivaluedMap<String, String> queryParamsMap = new MultivaluedHashMap();
        if(ApplicationUtil.isNotNullAndEmpty(queryParams)){
            queryParams.keySet().forEach((key) -> {
                queryParamsMap.putSingle(key, queryParams.get(key));
            });
            webResource.queryParams(queryParamsMap);
        }
        
        switch(method) {
            case GET:
                response = webResource
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", ("Bearer "+token))
                        .header("Content-Type", "application/json")
                        .get(ClientResponse.class);
                break;
            case POST:
                if(ApplicationUtil.isNotNullAndEmpty(bodyParams)) {
                    response = webResource
                            .accept(MediaType.APPLICATION_JSON)
                            .header("Authorization", ("Bearer "+token))
                            .header("Content-Type", "application/json")
                            .post(ClientResponse.class, GsonUtil.toJson(bodyParams));
                } else {
                    response = webResource
                            .accept(MediaType.APPLICATION_JSON)
                            .header("Content-Type", "application/json")
                            .header("Authorization", ("Bearer "+token))
                            .post(ClientResponse.class);       
                }
                break;
            case PUT: 
                if(ApplicationUtil.isNotNullAndEmpty(bodyParams)) {
                    response = webResource
                            .accept(MediaType.APPLICATION_JSON)
                            .header("Authorization", ("Bearer "+token))
                            .header("Content-Type", "application/json")
                            .put(ClientResponse.class, GsonUtil.toJson(bodyParams));
                } else {
                    response = webResource
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", ("Bearer "+token))
                        .header("Content-Type", "application/json")
                        .put(ClientResponse.class);
                }
                break;
            case DELETE:
                response = webResource
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", ("Bearer "+token))
                        .header("Content-Type", "application/json")
                        .delete(ClientResponse.class);
                break;
            default:
                break;
        }
        
        jsonResponse = response != null ? response.getEntity(String.class) : jsonResponse;
        
        if(response.getStatus() >= 400) {
            throw new RegisterServiceException(jsonResponse);
        }
        
        return  jsonResponse;
    }
    
}
