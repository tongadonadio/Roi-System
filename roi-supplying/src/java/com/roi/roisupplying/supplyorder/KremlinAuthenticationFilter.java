
package com.roi.roisupplying.supplyorder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
@KremlinAuthentication
@Priority(Priorities.AUTHENTICATION)
public class KremlinAuthenticationFilter implements ContainerRequestFilter {
    
    private static final String KREMLIN_AUTHENTICATION_KEY = "roi.kremlin.authentication.key";
    private static final String NOT_AUTHORIZED_MESSAGE = "Not authorized";
    
    @EJB
    private SettingsHelper settingsHelper;
 
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Extract the token from the HTTP Authorization header
        String token = getToken(authorizationHeader);
        if(ApplicationUtil.isNotNullAndEmpty(token)) {
            String kremlinKey = this.settingsHelper.getProperty(KREMLIN_AUTHENTICATION_KEY);
            boolean validToken = kremlinKey.equals(token);
            if(!validToken) {
                returnError(NOT_AUTHORIZED_MESSAGE, requestContext);
            }
        } else {
            returnError(NOT_AUTHORIZED_MESSAGE, requestContext);
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
