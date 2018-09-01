
package com.roi.roikremlin.controller;

import com.roi.roikremlin.registerservice.GsonUtil;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class KremlinExceptionMapper implements ExceptionMapper<RegisterServiceException> {

    @Override
    public Response toResponse(RegisterServiceException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(GsonUtil.toJson(exception.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
    
}
