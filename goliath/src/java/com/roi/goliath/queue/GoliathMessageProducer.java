package com.roi.goliath.queue;

import javax.ejb.Local;

@Local
public interface GoliathMessageProducer {
    public void sendMessage();
}
