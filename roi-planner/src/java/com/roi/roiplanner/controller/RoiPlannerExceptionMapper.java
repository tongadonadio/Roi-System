
package com.roi.roiplanner.controller;

import com.roi.roiplanner.supplyplan.GsonUtil;
import com.roi.roiplanner.supplyplan.SupplyPlanException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


public class RoiPlannerExceptionMapper implements ExceptionMapper<SupplyPlanException> {

    @Override
    public Response toResponse(SupplyPlanException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(GsonUtil.toJson(exception.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
    
}
