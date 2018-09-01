package com.roi.roisupplying.supplyorder.queue;

import com.roi.roisupplying.supplyorder.SupplyOrderActionWrapper;
import javax.ejb.Local;

@Local
public interface SupplyOrderProducer {
    public void sendSupplyOrderToQueue(SupplyOrderActionWrapper supplyOrder);
}
