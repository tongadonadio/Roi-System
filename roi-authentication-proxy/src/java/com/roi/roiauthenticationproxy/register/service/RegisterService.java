
package com.roi.roiauthenticationproxy.register.service;

import com.sun.jersey.api.client.ClientResponse;
import javax.ejb.Local;

@Local
public interface RegisterService {
 
    ClientResponse unregisterApplicationById(Long id);
    ClientResponse getRegisteredApplicationById(Long id);
    ClientResponse getAllRegisteredApplications();
    ClientResponse register(String json);
    
}
