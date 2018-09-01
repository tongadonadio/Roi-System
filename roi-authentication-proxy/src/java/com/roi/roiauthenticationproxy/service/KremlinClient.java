
package com.roi.roiauthenticationproxy.service;

import com.roi.roiauthenticationproxy.authentication.SettingsHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;

@Stateless
@LocalBean
public class KremlinClient {
    
    private static final String KREMLIN_URL = "roi.kremlin.url";
    
    @EJB
    private SettingsHelper settingsHelper;

    public ClientResponse unregisterApplicationById(Long id) {
        Client client = Client.create();
        String url = settingsHelper.getProperty(KREMLIN_URL) + "/register/" + id;
        WebResource webResource = client.resource(url);
        return webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .delete(ClientResponse.class);
    }
    
    public ClientResponse getRegisteredApplicationById(Long id) {
        Client client = Client.create();
        String url = settingsHelper.getProperty(KREMLIN_URL) + "/register/" + id;
        WebResource webResource = client.resource(url);
        return webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .get(ClientResponse.class);
    }
  
    public ClientResponse getAllRegisteredApplications() {
        Client client = Client.create();
        String url = settingsHelper.getProperty(KREMLIN_URL) + "/register";
        WebResource webResource = client.resource(url);
        return webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .get(ClientResponse.class);
    }
    
    public ClientResponse register(String json) {
        Client client = Client.create();
        String url = settingsHelper.getProperty(KREMLIN_URL) + "/register";
        WebResource webResource = client.resource(url);
        return webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .post(ClientResponse.class, json);
    }
    
    public ClientResponse perform(String json) {
        Client client = Client.create();
        String url = settingsHelper.getProperty(KREMLIN_URL) + "/perform";
        WebResource webResource = client.resource(url);
        return webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .post(ClientResponse.class, json);
    }
    
}
