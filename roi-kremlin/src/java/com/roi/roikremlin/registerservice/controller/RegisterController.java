
package com.roi.roikremlin.registerservice.controller;

import com.roi.roikremlin.registerservice.GsonUtil;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.RegisteredApplicationEx;
import com.roi.roikremlin.registerservice.service.RegisterApplicationService;
import com.roi.roikremlin.registerservice.service.RegisterValidationService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("register")
public class RegisterController {
    
    @EJB
    private RegisterValidationService registerValidationService;
    
    @EJB
    private RegisterApplicationService registerApplicationService;

    public RegisterController() {
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response removeServiceRegisteredById(@PathParam("id")Long id) throws RegisterServiceException {
        this.registerApplicationService.unregisterApplicationById(id);
        return Response.status(Response.Status.OK).build();
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getServiceRegisteredById(@PathParam("id")Long id) throws RegisterServiceException {
        RegisteredApplicationEx registeredApplication = this.registerApplicationService.getRegisteredApplicationById(id);
        return Response.status(Response.Status.OK).entity(GsonUtil.toJson(registeredApplication)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServicesRegistered() throws RegisterServiceException {
        List<RegisteredApplicationEx> registeredApplications = this.registerApplicationService.getAllRegisteredApplications();
        return Response.status(Response.Status.OK).entity(GsonUtil.toJson(registeredApplications)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterServiceDTO body) throws RegisterServiceException {
        Response response;
        List<String> errorMessages = registerValidationService.validate(body);
        if (errorMessages.isEmpty()) {
            Long id = registerApplicationService.registerApplication(body);
            RegisteredApplicationEx reg = new RegisteredApplicationEx();
            reg.setId(id);
            response = Response.status(Response.Status.CREATED).entity(GsonUtil.toJson(reg)).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(GsonUtil.toJson(errorMessages)).build();
        }
        return response;
    }
    
}
