package com.roi.goliath.queue;

import com.roi.goliath.ApplicationUtil;
import com.roi.goliath.GsonUtil;
import com.roi.goliath.planapproval.NetworkSectionDTO;
import com.roi.goliath.planapproval.RoiLogger;
import com.roi.goliath.planapproval.SupplyPlanDTO;
import java.util.ArrayList;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/goliathQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class GoliathMessageConsumer implements MessageListener {

    @EJB
    private RoiLogger logger;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            String jsonResult = msg.getText();
            if (ApplicationUtil.isNotNullAndEmpty(jsonResult)) {
                SupplyPlanDTO supplyPlan = GsonUtil.fromJson(jsonResult, SupplyPlanDTO.class);
                ArrayList<NetworkSectionDTO> sectionsList = supplyPlan.getNetworkSections();
                if (ApplicationUtil.isNotNullAndEmpty(sectionsList)) {
                    sectionsList.forEach((section) -> {
                        logger.info(section.getIdSection().toString(), GoliathMessageConsumer.class);
                    });
                }
            }
        } catch (JMSException ex) {
            logger.error("Error while trying to process the supply order ", this.getClass());
        }
    }

}
