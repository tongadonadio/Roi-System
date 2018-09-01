package com.roi.goliath.queue;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import com.roi.goliath.planapproval.RoiLogger;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class GoliathMessageProducerImpl implements GoliathMessageProducer {
    
    @Resource(mappedName = "jms/goliathQueue")
    private Queue queue;
    
    @Resource(mappedName = "jms/goliathConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Inject
    private RoiLogger logger;
    
    public void sendMessage() {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            
            TextMessage msg = session.createTextMessage("Received");
            MessageProducer producer = session.createProducer(queue);
            producer.send(msg);
            
            session.close();
            connection.close();
        } catch (JMSException ex) {
            logger.error("Error while trying to send Goliath message: 'Received'", ex, this.getClass());
        }
    }
}
