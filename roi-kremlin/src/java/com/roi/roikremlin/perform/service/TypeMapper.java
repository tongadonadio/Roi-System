package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.registerservice.ApplicationUtil;
import com.roi.roikremlin.registerservice.RoiLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

@Singleton
@LocalBean
public class TypeMapper {
    
    @EJB
    private RoiLogger roiLogger;
    
    public <T> T castObjectType(String type, String value) {
        T returnValue = null;
        ParamTypes option = ParamTypes.getFromValue(type.toLowerCase());
        switch(option) {
            case STRING: 
                returnValue = (T)value;
                break;
            case BOOLEAN: 
                returnValue = (T)getBoolean(value);
                break;
            case INTEGER: 
                returnValue = (T)getInteger(value);
                break;
            case DOUBLE: 
                returnValue = (T)getDouble(value);
                break;
            case INT: 
                returnValue = (T)getInteger(value);
                break;
            case LIST_STRING: 
                returnValue = (T)getListString(value);
                break;
            case LIST_INTEGER: 
                returnValue = (T)getListInteger(value);
                break;
            case LIST_DOUBLE: 
                returnValue = (T)getListDouble(value);
                break;
        }
        return returnValue;
    }
    
    private static Boolean getBoolean(final String value) {
        Boolean returnValue = null;
        if(value.equals("true")) {
            returnValue = true;
        } else if(value.equals("false")) {
            returnValue = false;
        }
        return returnValue;
    }
    
    private Integer getInteger(final String value) {
        Integer returnValue = null;
        try {
            returnValue = Integer.parseInt(value);
        } catch(NumberFormatException nfe) {
           roiLogger.info(nfe.getMessage(), this.getClass());
        }
        return returnValue;
    }
    
    private Double getDouble(final String value) {
        Double returnValue = null;
        try {
            returnValue = Double.parseDouble(value);
        } catch(NumberFormatException nfe) {
           roiLogger.info(nfe.getMessage(), this.getClass());
        }
        return returnValue;
    }
    
    private static List<String> getListString(final String value) {
        List<String> stringList = null;
        String[] arrayResult = value.split(",");
        if(ApplicationUtil.isNotNullAndEmpty(arrayResult)) {
            stringList = Arrays.asList(arrayResult);
        }
        return stringList;
    }
    
    private List<Integer> getListInteger(final String value) {
        List<Integer> integerList = null;
        try {
            String[] arrayResult = value.split(",");
            if(ApplicationUtil.isNotNullAndEmpty(arrayResult)) {
                for(String str : arrayResult) {
                    Integer parsedInt = Integer.parseInt(str);
                    if(integerList == null) {
                        integerList = new ArrayList();
                    }
                    integerList.add(parsedInt);
                }
            }
        } catch(NumberFormatException nfe) {
            integerList = null;
        }
        
        return integerList;
    }
    
    private List<Double> getListDouble(final String value) {
        List<Double> doubleList = null;
        try {
            String[] arrayResult = value.split(",");
            if(ApplicationUtil.isNotNullAndEmpty(arrayResult)) {
                for(String str : arrayResult) {
                    Double parsedDouble = Double.parseDouble(str);
                    if(doubleList == null) {
                        doubleList = new ArrayList();
                    }
                    doubleList.add(parsedDouble);
                }
            }
        } catch(NumberFormatException nfe) {
            doubleList = null;
        }
        
        return doubleList;
    }

}
