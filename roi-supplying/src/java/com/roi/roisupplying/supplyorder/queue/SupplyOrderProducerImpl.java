package com.roi.roisupplying.supplyorder.queue;

import com.roi.roisupplying.supplyorder.RoiLogger;
import com.roi.roisupplying.supplyorder.SupplyOrderActionWrapper;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

@Stateless
public class SupplyOrderProducerImpl implements SupplyOrderProducer {

    @Resource(mappedName = "jms/supplyOrderQueue")
    private Queue supplyOrderQueue;

    @Resource(mappedName = "jms/supplyOrderQueueFactory")
    private ConnectionFactory connectionFactory;
    
    @Inject
    private RoiLogger logger;
    
    @Override
    public void sendSupplyOrderToQueue(SupplyOrderActionWrapper supplyOrder) {
         sendJMSMessageToSupplyOrderQueue(supplyOrder);
    }

    private void sendJMSMessageToSupplyOrderQueue(SupplyOrderActionWrapper supplyOrder) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            MessageProducer producer = session.createProducer(supplyOrderQueue);
            ObjectMessage objectMessage = session.createObjectMessage(supplyOrder);
            producer.send(objectMessage);
        } catch(JMSException e) {
            logger.error("Error while trying to send Supply Order: " + supplyOrder.getSupplyOrder().getOrderNumber() + " message to the queue", e, this.getClass());
        }
    }

}
