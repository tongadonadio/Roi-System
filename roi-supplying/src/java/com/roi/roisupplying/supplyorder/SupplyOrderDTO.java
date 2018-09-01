package com.roi.roisupplying.supplyorder;

import java.io.Serializable;
import java.util.Date;

public class SupplyOrderDTO implements Serializable {
    private Long orderNumber;
    private Long clientNumber;
    private Date supplyStartDate;
    private Long volume;
    private Long servicePointIdentifier;
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
