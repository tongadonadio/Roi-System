
package com.roi.roiplanner.supplyplan.service;

import com.roi.roiplanner.supplyplan.NetworkSectionDTO;
import com.roi.roiplanner.supplyplan.SupplyPlanDTO;
import com.roi.roiplanner.supplyplan.SupplyPlanException;
import com.roi.roiplanner.supplyplan.dao.SupplyPlanDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SupplyPlanServiceImpl implements SupplyPlanService{
    
    @EJB
    private SupplyPlanDAO supplyPlanDAO;
    
    @EJB
    private NetworkSectionClient networkSectionClient;
    
    @EJB
    private LogPlanClient logPlanClient;

    @Override
    public SupplyPlanDTO getSupplyPlanById(Long supplyPlanId) throws SupplyPlanException {
        return this.supplyPlanDAO.getSupplyPlanById(supplyPlanId);
    }

    @Override
    public List<SupplyPlanDTO> getAllSupplyPlans() throws SupplyPlanException {
        return this.supplyPlanDAO.getAll();
    }

    @Override
    public void cancelSupplyPlan(Long supplyPlanId) throws SupplyPlanException {
        this.supplyPlanDAO.cancelSupplyPlan(supplyPlanId);
    }

    @Override
    public SupplyPlanDTO modifySupplyPlan(Long supplyPlanId) throws SupplyPlanException {
        SupplyPlanDTO supplyPlan = this.supplyPlanDAO.getSupplyPlanById(supplyPlanId);
        List<NetworkSectionDTO> networkSections = networkSectionClient.getNetworkSections(supplyPlan.getOrderId());
        SupplyPlanDTO newPlan = new SupplyPlanDTO();
        newPlan.setOrderId(supplyPlan.getOrderId());
        newPlan.setNetworkSections(networkSections);
        newPlan.setStatus("OK");
        
        this.supplyPlanDAO.update(newPlan, supplyPlanId);
        return newPlan;
    }

    @Override
    public Long createSupplyPlan(Long orderId) throws SupplyPlanException {
        List<NetworkSectionDTO> networkSections = networkSectionClient.getNetworkSections(orderId);
        SupplyPlanDTO newPlan = new SupplyPlanDTO();
        newPlan.setOrderId(orderId);
        newPlan.setNetworkSections(networkSections);
        newPlan.setStatus("OK");
        
        return this.supplyPlanDAO.save(newPlan);
    }

    @Override
    public SupplyPlanDTO getSupplyPlanByOrderId(Long orderId) throws SupplyPlanException {
        return this.supplyPlanDAO.getSupplyPlanByOrderId(orderId);
    }

    @Override
    public void approvePlan(Long supplyPlanId) throws SupplyPlanException {
        SupplyPlanDTO supplyPlan = this.supplyPlanDAO.approve(supplyPlanId);
        this.logPlanClient.logPlan(supplyPlan);
    }
    
}
