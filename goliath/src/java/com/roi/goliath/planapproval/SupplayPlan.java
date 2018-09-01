package com.roi.goliath.planapproval;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "supply_plan")
public class SupplayPlan implements Serializable {
    @Id
    @Column(name = "id_plan")
    private Long idPlan;
}
