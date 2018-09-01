package com.roi.roiauthenticationproxy.authentication.controller;

import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.GsonUtil;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {
    @Override
    public Response toResponse(AuthenticationException exception) {
        Map<String, String> errorsMap = new HashMap();
        errorsMap.put("error", exception.getMessage());
        return Response.status(Response.Status.CONFLICT).entity(GsonUtil.toJson(errorsMap)).type(MediaType.APPLICATION_JSON).build();
    }
}
