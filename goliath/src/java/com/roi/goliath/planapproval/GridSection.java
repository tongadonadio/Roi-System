package com.roi.goliath.planapproval;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grid_section")
public class GridSection implements Serializable{
    @Id
    @Column(name = "id_supply_point")
    private Long idSupplyPoint;
    @Id
    @Column(name = "id_actuator")
    private Long idActuator;
    @Id
    @Column(name = "id_section")
    private Long idSection;
    @Id
    @Column(name = "id_supply_plan")
    private Long idSupplyPlan; 

    public Long getIdSupplyPoint() {
        return idSupplyPoint;
    }

    public void setIdSupplyPoint(Long idSupplyPoint) {
        this.idSupplyPoint = idSupplyPoint;
    }

    public Long getIdActuator() {
        return idActuator;
    }

    public void setIdActuator(Long idActuator) {
        this.idActuator = idActuator;
    }

    public Long getIdSection() {
        return idSection;
    }

    public void setIdSection(Long idSection) {
        this.idSection = idSection;
    }

    public Long getIdSupplyPlan() {
        return idSupplyPlan;
    }

    public void setIdSupplyPlan(Long idSupplyPlan) {
        this.idSupplyPlan = idSupplyPlan;
    }
}
