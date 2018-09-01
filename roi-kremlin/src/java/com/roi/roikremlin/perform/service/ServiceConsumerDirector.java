
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.registerservice.RoiLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Singleton
@LocalBean
public class ServiceConsumerDirector {
        
    private static final String REST = "REST";
    private static final String JMS = "JMS";
    
    @EJB
    private RoiLogger roiLogger;
    
    @EJB(lookup = "java:global/roi-kremlin/RestServiceConsumer!com.roi.roikremlin.perform.service.ServiceConsumer")
    private ServiceConsumer restServiceConsumer;
    
    @EJB(lookup = "java:global/roi-kremlin/JmsServiceConsumer!com.roi.roikremlin.perform.service.ServiceConsumer")
    private ServiceConsumer jmsServiceConsumer;
    
    public ServiceConsumer getServiceConsumer(String type) {
        ServiceConsumer consumer = null;

        switch(type) {
            case REST: consumer = restServiceConsumer;
            break;
            case JMS: consumer = jmsServiceConsumer;
            break;          
        }
        
        return consumer;
    }
    
}
