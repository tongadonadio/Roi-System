package com.roi.roiauthenticationproxy.authentication.controller;

import com.roi.roiauthenticationproxy.authentication.filter.JwtTokenNeeded;
import com.roi.roiauthenticationproxy.authentication.ApplicationUtil;
import com.roi.roiauthenticationproxy.authentication.UserDTO;
import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.service.UserService;
import com.roi.roiauthenticationproxy.authentication.service.UserValidationService;
import com.roi.roiauthenticationproxy.authentication.GsonUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserController {
    
    @EJB
    private UserService userService;
    
    @EJB
    private UserValidationService userValidationService;

    public UserController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUser(@PathParam("id")Long id) throws AuthenticationException {
        Response response;
        UserDTO user = userService.getUser(id);
        if(user == null) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            response = Response.status(Response.Status.OK).entity(GsonUtil.toJson(user)).build();
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    public Response createUser(@HeaderParam("Authorization") String token, UserDTO user) throws AuthenticationException {
        Response response;
        validateAdminUser(token);
        List<String> errorMessages = userValidationService.validate(user);
        if (errorMessages.isEmpty()) {
            Long userId = userService.createUser(user);
            user.setId(userId);
            response = Response.status(Response.Status.CREATED).entity(GsonUtil.toJson(user)).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(GsonUtil.toJson(errorMessages)).build();
        }
        return response;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @JwtTokenNeeded
    public Response updateUser(@HeaderParam("Authorization") String token, @PathParam("id")Long id, UserDTO user) throws AuthenticationException {
        Response response;
        validateAdminUser(token);
        List<String> errorMessages = userValidationService.validate(user);
        if (errorMessages.isEmpty()) {
            if (userService.getUser(id) == null) {
                response = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                user.setId(id);
                UserDTO updatedUser = userService.updateUser(user);
                response = Response.ok().entity(updatedUser).build();
            }
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(GsonUtil.toJson(errorMessages)).build();
        }
        return response;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    @Path("{id}")
    public Response deleteUser(@HeaderParam("Authorization") String token, @PathParam("id")Long id) throws AuthenticationException {
        Response response;
        validateAdminUser(token);
        UserDTO user = userService.getUser(id);
        if (user == null) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            userService.deleteUser(id);
            response = Response.ok().build();
        }
        return response;
    }
    
    private void validateAdminUser(String token) throws AuthenticationException {
        UserDTO adminUser = userService.getUserFromToken(ApplicationUtil.removeFromString(token, "Bearer"));
        String errorMessage = userValidationService.validateAdminUser(adminUser);
        if(errorMessage != null) {
            throw new AuthenticationException(errorMessage);
        }
    }

}
