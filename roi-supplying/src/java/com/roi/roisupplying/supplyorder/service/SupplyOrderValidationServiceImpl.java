package com.roi.roisupplying.supplyorder.service;

import com.roi.roisupplying.supplyorder.MessageHelper;
import com.roi.roisupplying.supplyorder.SupplyOrderDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SupplyOrderValidationServiceImpl implements SupplyOrderValidationService {
    
    @Inject
    private MessageHelper messages;

    @Override
    public List<String> validateOrder(SupplyOrderDTO supplyOrder) {
        List<String> errorMessages = new ArrayList();
        validateNotNull(supplyOrder.getOrderNumber(), "orderNumber", errorMessages);
        validateNotNull(supplyOrder.getClientNumber(), "clientNumber", errorMessages);
        validateNotNull(supplyOrder.getClosingDay(), "closingDay", errorMessages);
        validateNotNull(supplyOrder.getServicePointIdentifier(), "servicePointIdentifier", errorMessages);
        validateNotNull(supplyOrder.getVolume(), "volume", errorMessages);
        return errorMessages;
    }

    private <T> void validateNotNull(T value, String fieldName, List<String> errorMessages) {
        if (value == null) {
            errorMessages.add(messages.getMessage("supplyorder.validation.error", fieldName));
        }
    }
 }
