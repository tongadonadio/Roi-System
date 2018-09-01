package com.roi.roisupplying.supplyorder.controller;

import com.roi.roisupplying.supplyorder.GsonUtil;
import com.roi.roisupplying.supplyorder.KremlinAuthentication;
import com.roi.roisupplying.supplyorder.RoiLogger;
import com.roi.roisupplying.supplyorder.SupplyOrderDTO;
import com.roi.roisupplying.supplyorder.OrderAction;
import com.roi.roisupplying.supplyorder.SupplyOrderException;
import com.roi.roisupplying.supplyorder.queue.SupplyOrderProducer;
import com.roi.roisupplying.supplyorder.service.SupplyOrderService;
import com.roi.roisupplying.supplyorder.service.SupplyOrderValidationService;
import com.roi.roisupplying.supplyorder.SupplyOrderActionWrapper;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("supplyorder")
public class SupplyOrderController {

    @EJB
    private SupplyOrderService supplyOrderService;

    @EJB
    private SupplyOrderValidationService supplyOrderValidationService;

    @EJB
    private SupplyOrderProducer supplyOrderProducer;

    @Inject
    private RoiLogger logger;
    
    public SupplyOrderController() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{orderNumber}")
    @KremlinAuthentication
    public Response getOrder(@PathParam("orderNumber")Long orderNumber) throws SupplyOrderException {
        Response response;
        SupplyOrderDTO supplyOrder = supplyOrderService.getOrder(orderNumber);
        if(supplyOrder == null) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            response = Response.status(Response.Status.OK).entity(GsonUtil.toJson(supplyOrder)).build();
        }
        return response;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @KremlinAuthentication
    public Response createOrder(SupplyOrderDTO supplyOrder) {
        Response response;
        List<String> errorMessages = supplyOrderValidationService.validateOrder(supplyOrder);
        if (errorMessages.isEmpty()) {
            logger.info("Sending supply order to queue", this.getClass());
            supplyOrderProducer.sendSupplyOrderToQueue(new SupplyOrderActionWrapper(supplyOrder, OrderAction.CREATE));
            response = Response.status(Response.Status.CREATED).entity(GsonUtil.toJson(supplyOrder)).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(GsonUtil.toJson(errorMessages)).build();
        }
        return response;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @KremlinAuthentication
    public Response updateOrder(SupplyOrderDTO supplyOrder) throws SupplyOrderException {
        Response response;
        List<String> errorMessages = supplyOrderValidationService.validateOrder(supplyOrder);
        if (errorMessages.isEmpty()) {
            if (supplyOrderService.getOrder(supplyOrder.getOrderNumber()) == null) {
                response = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                supplyOrderProducer.sendSupplyOrderToQueue(new SupplyOrderActionWrapper(supplyOrder, OrderAction.MODIFY));
                response = Response.ok().build();
            }
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(GsonUtil.toJson(errorMessages)).build();
        }
        return response;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{orderNumber}")
    @KremlinAuthentication
    public Response deleteOrder(@PathParam("orderNumber")Long orderNumber) throws SupplyOrderException {
        Response response;
        SupplyOrderDTO supplyOrder = supplyOrderService.getOrder(orderNumber);
        if (supplyOrder == null) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            supplyOrderProducer.sendSupplyOrderToQueue(new SupplyOrderActionWrapper(supplyOrder, OrderAction.DELETE));
            response = Response.ok().build();
        }
        return response;
    }
    
}
