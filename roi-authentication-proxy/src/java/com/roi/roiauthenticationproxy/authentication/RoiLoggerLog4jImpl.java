package com.roi.roiauthenticationproxy.authentication;

import org.apache.log4j.Logger;

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
    public void error(String message, Throwable exception, Class objectClass) {
        Logger.getLogger(objectClass).error(message, exception);
    }
}
