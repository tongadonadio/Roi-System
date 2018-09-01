package com.roi.roiauthenticationproxy.authentication.controller;

import com.roi.roiauthenticationproxy.authentication.UserDTO;
import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.service.UserValidationService;
import com.roi.roiauthenticationproxy.authentication.GsonUtil;
import com.roi.roiauthenticationproxy.authentication.ApplicationUtil;
import com.roi.roiauthenticationproxy.authentication.service.AuthenticationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class AuthenticationController {

    @EJB
    private UserValidationService userValidationService;
    
    @EJB
    private AuthenticationService authenticationService;
    
    public AuthenticationController() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(UserDTO user) throws AuthenticationException {
        Response response = Response.status(Response.Status.UNAUTHORIZED).build();
        List<String> errorMessages = userValidationService.validate(user);
        if(errorMessages.isEmpty()) {
            String token = authenticationService.authenticate(user);
            if(ApplicationUtil.isNotNullAndEmpty(token)) {
                Map<String, String> responseMap = new HashMap();
                responseMap.put("token", token);
                response = Response.ok().entity(GsonUtil.toJson(responseMap)).build();
            }
        }
        return response;
    }
}
