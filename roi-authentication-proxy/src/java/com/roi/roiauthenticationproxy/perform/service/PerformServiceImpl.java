
package com.roi.roiauthenticationproxy.perform.service;

import com.roi.roiauthenticationproxy.service.KremlinClient;
import com.sun.jersey.api.client.ClientResponse;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PerformServiceImpl implements PerformService{

    @EJB
    private KremlinClient kremlinClient;
    
    @Override
    public ClientResponse perform(String json) {
        return this.kremlinClient.perform(json);
    }
    
}
