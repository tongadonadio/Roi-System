
package com.roi.roiauthenticationproxy.perform.service;

import com.sun.jersey.api.client.ClientResponse;
import javax.ejb.Local;

@Local
public interface PerformService {
   
    ClientResponse perform(String json);
    
}
