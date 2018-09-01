package com.roi.roisupplying.supplyorder.controller;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.roi.roisupplying.supplyorder.KremlinAuthenticationFilter.class);
        resources.add(com.roi.roisupplying.supplyorder.controller.SupplyOrderController.class);
        resources.add(com.roi.roisupplying.supplyorder.controller.SupplyOrderExceptionMapper.class);
    }
}
