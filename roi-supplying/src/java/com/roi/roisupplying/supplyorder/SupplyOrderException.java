package com.roi.roisupplying.supplyorder;


public class SupplyOrderException extends Exception {
    
    public SupplyOrderException(String message) {
        super(message);
    }

    public SupplyOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
