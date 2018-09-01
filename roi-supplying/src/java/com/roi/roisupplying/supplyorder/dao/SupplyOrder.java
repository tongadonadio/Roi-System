package com.roi.roisupplying.supplyorder.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "supply_order")
public class SupplyOrder implements Serializable {
    @Id
    @Column(name = "order_number")
    private Long orderNumber;
    @Column(name = "client_number")
    private Long clientNumber;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "supply_start_date")
    private Date supplyStartDate;
    @Column(name = "volume")
    private Long volume;
    @Column(name = "service_point_identifier")
    private Long servicePointIdentifier;
    @Column(name = "closing_day")
    private Integer closingDay;

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(Long clientNumber) {
        this.clientNumber = clientNumber;
    }

    public Date getSupplyStartDate() {
        return supplyStartDate;
    }

    public void setSupplyStartDate(Date supplyStartDate) {
        this.supplyStartDate = supplyStartDate;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getServicePointIdentifier() {
        return servicePointIdentifier;
    }

    public void setServicePointIdentifier(Long servicePointIdentifier) {
        this.servicePointIdentifier = servicePointIdentifier;
    }

    public Integer getClosingDay() {
        return closingDay;
    }

    public void setClosingDay(Integer closingDay) {
        this.closingDay = closingDay;
    }
}
