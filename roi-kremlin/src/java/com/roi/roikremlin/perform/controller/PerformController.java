
package com.roi.roikremlin.perform.controller;

import com.roi.roikremlin.perform.PerformRequestDTO;
import com.roi.roikremlin.perform.service.PerformRequestService;
import com.roi.roikremlin.perform.service.PerformValidationService;
import com.roi.roikremlin.registerservice.GsonUtil;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("perform")
public class PerformController {

    public PerformController() {
    }
    
    @EJB
    private PerformValidationService performValidationService;
    
    @EJB
    private PerformRequestService performRequestService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response perform(PerformRequestDTO performRequest) throws RegisterServiceException {
        Response response;
        
        List<String> errorMessages = this.performValidationService.validate(performRequest);
        if (errorMessages.isEmpty()) {
            String externalResponse = performRequestService.performRequest(performRequest);
            if(externalResponse != null) {
                response = Response.status(Response.Status.OK).entity(externalResponse).build();
            } else {
                response = Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(GsonUtil.toJson(errorMessages)).build();
        }
        return response;
    }
    
}
