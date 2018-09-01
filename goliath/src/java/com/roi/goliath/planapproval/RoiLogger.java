package com.roi.goliath.planapproval;

import javax.ejb.Local;

@Local
public interface RoiLogger {
    public void info(String message, Class objectClass);
    public void debug(String message, Class objectClass);
    public void warn(String message, Class objectClass);
    public void error(String message, Class objectClass);
    public void error(String message, Throwable exception, Class objectClass);
}
