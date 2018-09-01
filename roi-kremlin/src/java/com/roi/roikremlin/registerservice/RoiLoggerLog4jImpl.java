package com.roi.roikremlin.registerservice;

import javax.ejb.Stateless;
import org.apache.log4j.Logger;

@Stateless
public class RoiLoggerLog4jImpl implements RoiLogger {

    @Override
    public void info(String message, Class objectClass) {
        Logger.getLogger(objectClass).info(message);
    }

    @Override
    public void debug(String message, Class objectClass) {
        Logger.getLogger(objectClass).debug(message);
    }

    @Override
    public void warn(String message, Class objectClass) {
        Logger.getLogger(objectClass).warn(message);
    }

    @Override
    public void error(String message, Class objectClass) {
        Logger.getLogger(objectClass).error(message);
    }
    
    @Override
    public void error(String message, Throwable throwable, Class objectClass) {
        Logger.getLogger(objectClass).error(message, throwable);
    }
}
