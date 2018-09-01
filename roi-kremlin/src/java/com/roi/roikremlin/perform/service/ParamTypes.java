package com.roi.roikremlin.perform.service;

public enum ParamTypes {
    
    STRING("string"),
    BOOLEAN("boolean"),
    INTEGER("integer"),
    DOUBLE("double"),
    INT("int"),
    LIST_STRING("list<string>"),
    LIST_INTEGER("list<integer>"),
    LIST_DOUBLE("list<double>");

    private final String name;
    
    private ParamTypes(String name) {
        this.name = name;
    }
    
    public String getName() {
       return this.name; 
    }
    
    static ParamTypes getFromValue(String name) {
        ParamTypes type = STRING;
        for(ParamTypes paramType : ParamTypes.values()) {
            if(paramType.getName().equals(name)) {
                type = paramType;
            }
        }
        return type;
    }
}
