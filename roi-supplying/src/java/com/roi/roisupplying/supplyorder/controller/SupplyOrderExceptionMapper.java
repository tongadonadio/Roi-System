package com.roi.roisupplying.supplyorder.controller;

import com.roi.roisupplying.supplyorder.GsonUtil;
import com.roi.roisupplying.supplyorder.SupplyOrderException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SupplyOrderExceptionMapper implements ExceptionMapper<SupplyOrderException> {

    @Override
    public Response toResponse(SupplyOrderException exception) {
        return Response.status(Response.Status.CONFLICT).entity(GsonUtil.toJson(exception.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
}
