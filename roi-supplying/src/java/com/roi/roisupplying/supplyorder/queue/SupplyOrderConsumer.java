package com.roi.roisupplying.supplyorder.queue;

import com.roi.roisupplying.supplyorder.RoiLogger;
import com.roi.roisupplying.supplyorder.SupplyOrderDTO;
import com.roi.roisupplying.supplyorder.OrderAction;
import com.roi.roisupplying.supplyorder.SupplyOrderException;
import com.roi.roisupplying.supplyorder.service.SupplyOrderService;
import com.roi.roisupplying.supplyorder.SupplyOrderActionWrapper;
import com.roi.roisupplying.supplyorder.service.SupplyPlanClient;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/supplyOrderQueue"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class SupplyOrderConsumer implements MessageListener {

    @EJB
    private SupplyOrderService supplyOrderService;
    
    @Inject
    private RoiLogger logger;
    
    @EJB
    private SupplyPlanClient supplyPlanClient;
    
    @Override
    public void onMessage(Message message) {
        OrderAction action = null;
        SupplyOrderDTO supplyOrder = null;
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            SupplyOrderActionWrapper supplyOrderAction = (SupplyOrderActionWrapper) objectMessage.getObject();
            action = supplyOrderAction.getAction();
            supplyOrder = supplyOrderAction.getSupplyOrder();
            switch(action) {
                case CREATE:
                    supplyOrderService.createOrder(supplyOrder);
                    supplyPlanClient.createSupplyPlan(supplyOrder.getOrderNumber());
                    break;
                case MODIFY:
                    supplyOrderService.updateOrder(supplyOrder);
                    supplyPlanClient.modifyOrderPlan(supplyOrder.getOrderNumber());
                    break;
                case DELETE:
                    supplyOrderService.deleteOrder(supplyOrder.getOrderNumber());
                    supplyPlanClient.deleteOrderPlan(supplyOrder.getOrderNumber());
                    break;
                default:
                    break;
            }
        } catch(JMSException e) {
            logger.error("Error while trying to process the supply order " , this.getClass());
        } catch(SupplyOrderException e) {
            Long orderNumber = supplyOrder != null ? supplyOrder.getOrderNumber() : null;
            logger.error("An error occured while trying to " + action +  " for " +  orderNumber, this.getClass());
        }
    }

}
