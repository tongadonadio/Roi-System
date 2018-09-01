package com.roi.roisupplying.supplyorder;

import java.io.Serializable;

public class SupplyOrderActionWrapper implements Serializable {
    private SupplyOrderDTO supplyOrder;
    private OrderAction action;

    public SupplyOrderActionWrapper(SupplyOrderDTO supplyOrder, OrderAction action) {
        this.supplyOrder = supplyOrder;
        this.action = action;
    }

    public SupplyOrderDTO getSupplyOrder() {
        return supplyOrder;
    }

    public void setSupplyOrder(SupplyOrderDTO supplyOrder) {
        this.supplyOrder = supplyOrder;
    }

    public OrderAction getAction() {
        return action;
    }

    public void setAction(OrderAction action) {
        this.action = action;
    }
}
