
package com.roi.roiplanner.supplyplan.controller;

import com.roi.roiplanner.supplyplan.GsonUtil;
import com.roi.roiplanner.supplyplan.KremlinAuthentication;
import com.roi.roiplanner.supplyplan.SupplyPlanDTO;
import com.roi.roiplanner.supplyplan.SupplyPlanException;
import com.roi.roiplanner.supplyplan.service.SupplyPlanService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("supplyplan")
public class SupplyPlanController {
    
    @EJB
    private SupplyPlanService supplyPlanService;

    public SupplyPlanController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @KremlinAuthentication
    public Response getAllPlans() throws SupplyPlanException {
        List<SupplyPlanDTO> supplyPlans = this.supplyPlanService.getAllSupplyPlans();
        return Response.status(Response.Status.OK).entity(GsonUtil.toJson(supplyPlans)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @KremlinAuthentication
    public Response getPlanById(@PathParam("id")Long id) throws SupplyPlanException {
        SupplyPlanDTO supplyPlan = this.supplyPlanService.getSupplyPlanById(id);
        return Response.status(Response.Status.OK).entity(GsonUtil.toJson(supplyPlan)).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("order/{id}")
    @KremlinAuthentication
    public Response getPlanByOrderId(@PathParam("id")Long id) throws SupplyPlanException {
        SupplyPlanDTO supplyPlan = this.supplyPlanService.getSupplyPlanByOrderId(id);
        return Response.status(Response.Status.OK).entity(GsonUtil.toJson(supplyPlan)).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @KremlinAuthentication
    public Response modifyPlan(@PathParam("id")Long id) throws SupplyPlanException {
        SupplyPlanDTO supplyPlan = this.supplyPlanService.modifySupplyPlan(id);
        return Response.status(Response.Status.OK).entity(GsonUtil.toJson(supplyPlan)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @KremlinAuthentication
    public Response createPlan(@PathParam("id")Long id) throws SupplyPlanException {
        Long supplyPlanId = this.supplyPlanService.createSupplyPlan(id);
        SupplyPlanDTO plan = new SupplyPlanDTO();
        plan.setId(supplyPlanId);
        return Response.status(Response.Status.CREATED).entity(GsonUtil.toJson(plan)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/approve")
    @KremlinAuthentication
    public Response approvePlan(@PathParam("id")Long id) throws SupplyPlanException {
        this.supplyPlanService.approvePlan(id);
        return Response.status(Response.Status.OK).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @KremlinAuthentication
    public Response deletePlan(@PathParam("id")Long id) throws SupplyPlanException {
        this.supplyPlanService.cancelSupplyPlan(id);
        return Response.status(Response.Status.OK).build();
    }
    
}
