
package com.roi.roiplanner.supplyplan.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "network_section")
public class NetworkSection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "source_id")
    private String sourceId;
    
    @Column(name = "actuator_id")
    private String actuatorId;
    
    @Column(name = "section_id")
    private String sectionId;
    
    @ManyToOne
    @JoinTable(name="supply_plan_network_section_link")
    private SupplyPlan supplyPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(String actuatorId) {
        this.actuatorId = actuatorId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public SupplyPlan getSupplyPlan() {
        return supplyPlan;
    }

    public void setSupplyPlan(SupplyPlan supplyPlan) {
        this.supplyPlan = supplyPlan;
    }
    
}
