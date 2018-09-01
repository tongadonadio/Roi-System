
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.registerservice.GsonUtil;
import com.roi.roikremlin.registerservice.RoiLogger;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless
public class JmsServiceClientImpl implements JmsServiceClient{
    
    @EJB
    private RoiLogger logger;
    
    @Override
    public String callService(String host, Integer port, String queueName, String connectionFactoryName, Map<String, Object> bodyParams) {
        String response = "OK";
        try {
            Properties props = new Properties();
            props.put(Context.PROVIDER_URL, getUrl(host, port));

            InitialContext ctx = new InitialContext(props);
            // JNDI Lookup for QueueConnectionFactory in remote JMS Provider
            QueueConnectionFactory qFactory = (QueueConnectionFactory) ctx.lookup(connectionFactoryName);

            // Create a Connection from QueueConnectionFactory
            Connection connection = qFactory.createConnection();
            logger.info("Connection established with JMS Provide", this.getClass());

            // Initialise the communication session 
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the message
            TextMessage message = session.createTextMessage();
            message.setText(GsonUtil.toJson(bodyParams));

            // JNDI Lookup for the Queue in remote JMS Provider
            Queue queue = (Queue) ctx.lookup(queueName);

            MessageProducer producer = session.createProducer(queue);
            producer.send(message);

        } catch(JMSException | NamingException e) {
            logger.error("Error while trying to connect to a remote queue", e, this.getClass());
            response = null;
        } 
        return response;
    }
    
    private String getUrl(String host, Integer port) {
        StringBuilder sb = new StringBuilder();
        sb.append("mq://");
        sb.append(host);
        sb.append(":");
        sb.append(port.toString());
        return sb.toString();
    }   
    
}