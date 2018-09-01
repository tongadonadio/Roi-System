package com.roi.roiauthenticationproxy.authentication.filter;

import com.roi.roiauthenticationproxy.authentication.ApplicationUtil;
import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.GsonUtil;
import com.roi.roiauthenticationproxy.authentication.MessageHelper;
import com.roi.roiauthenticationproxy.authentication.RoiLogger;
import com.roi.roiauthenticationproxy.authentication.service.AuthenticationService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@JwtTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {
    
    @EJB
    private AuthenticationService authenticatonService;
    
    @Inject
    private RoiLogger logger;
    
    @Inject
    private MessageHelper messages;
 
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
 
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Extract the token from the HTTP Authorization header
        String token = getToken(authorizationHeader);
        if(ApplicationUtil.isNotNullAndEmpty(token)) {
            try {
                Boolean validToken = authenticatonService.validateUserToken(token);
                if(!validToken) {
                    returnError(messages.getMessage("authentication.filter.token.error"), requestContext);
                }
            } catch (AuthenticationException e) {
                logger.error(e.getMessage(), this.getClass());
                returnError(e.getMessage(), requestContext);
            }   
        } else {
            returnError(messages.getMessage("authentication.filter.missingtoken.error"), requestContext);
        }
    }
    
    private String getToken(String authorizationHeader) {
        String token = null;
        if(authorizationHeader != null) {
            token = ApplicationUtil.removeFromString(authorizationHeader, "Bearer");
        }
        return token;
    }
    
    private void returnError(String errorMessage, ContainerRequestContext requestContext) {
        Map<String, String> errorsMap = new HashMap();
        errorsMap.put("error", errorMessage);
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(GsonUtil.toJson(errorsMap)).type(MediaType.APPLICATION_JSON).build());
    }
}
