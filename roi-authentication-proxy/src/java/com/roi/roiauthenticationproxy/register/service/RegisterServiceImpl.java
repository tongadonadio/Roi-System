
package com.roi.roiauthenticationproxy.register.service;

import com.roi.roiauthenticationproxy.service.KremlinClient;
import com.sun.jersey.api.client.ClientResponse;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class RegisterServiceImpl implements RegisterService {
    
    @EJB
    private KremlinClient kremlinClient;

    @Override
    public ClientResponse unregisterApplicationById(Long id) {
        return this.kremlinClient.unregisterApplicationById(id);
    }

    @Override
    public ClientResponse getRegisteredApplicationById(Long id) {
        return this.kremlinClient.getRegisteredApplicationById(id);
    }

    @Override
    public ClientResponse getAllRegisteredApplications() {
        return this.kremlinClient.getAllRegisteredApplications();
    }

    @Override
    public ClientResponse register(String json) {
        return this.kremlinClient.register(json);
    }
    
}
