
package com.roi.roiauthenticationproxy.register.controller;

import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.GsonUtil;
import com.roi.roiauthenticationproxy.register.RegisterServiceDTO;
import com.roi.roiauthenticationproxy.register.service.RegisterService;
import com.sun.jersey.api.client.ClientResponse;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("register")
@RequestScoped
public class RegisterController {

    public RegisterController() {
    }
    
    @EJB
    private RegisterService registerService;

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response removeServiceRegisteredById(@PathParam("id")Long id) throws AuthenticationException {
        ClientResponse response = registerService.unregisterApplicationById(id);
        return Response.status(response.getStatus()).build();
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getServiceRegisteredById(@PathParam("id")Long id) throws AuthenticationException {
        ClientResponse response = registerService.getRegisteredApplicationById(id);
        String jsonResponse = response.getEntity(String.class);
        return Response.status(response.getStatus()).entity(jsonResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServicesRegistered() throws AuthenticationException {
        ClientResponse response = registerService.getAllRegisteredApplications();
        String jsonResponse = response.getEntity(String.class);
        return Response.status(response.getStatus()).entity(jsonResponse).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterServiceDTO body) throws AuthenticationException {
        String json = GsonUtil.toJson(body);
        ClientResponse response = registerService.register(json);
        String jsonResponse = response.getEntity(String.class);
        return Response.status(response.getStatus()).entity(jsonResponse).build();
    }
    
}
