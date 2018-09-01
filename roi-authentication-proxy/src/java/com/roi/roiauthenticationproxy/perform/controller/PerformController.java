
package com.roi.roiauthenticationproxy.perform.controller;

import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.GsonUtil;
import com.roi.roiauthenticationproxy.perform.PerformRequestDTO;
import com.roi.roiauthenticationproxy.perform.service.PerformService;
import com.sun.jersey.api.client.ClientResponse;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("perform")
@RequestScoped
public class PerformController {

    public PerformController() {
    }
    
    @EJB
    private PerformService performService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response perform(PerformRequestDTO performRequest) throws AuthenticationException {
        String json = GsonUtil.toJson(performRequest);
        ClientResponse response = performService.perform(json);
        String jsonResponse = response.getEntity(String.class);
        return Response.status(response.getStatus()).entity(jsonResponse).build();
    }
    
}
