
package com.roi.roiplanner.supplyplan.dao;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "supply_plan")
public class SupplyPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SupplyPlanStatus status;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "supplyPlan", cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<NetworkSection> networkSections;
    
    @Column(name = "order_id")
    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplyPlanStatus getStatus() {
        return status;
    }

    public void setStatus(SupplyPlanStatus status) {
        this.status = status;
    }

    public List<NetworkSection> getNetworkSections() {
        return networkSections;
    }

    public void setNetworkSections(List<NetworkSection> networkSections) {
        this.networkSections = networkSections;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
}
