
package com.roi.roikremlin.controller;

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
        resources.add(com.roi.roikremlin.controller.KremlinExceptionMapper.class);
        resources.add(com.roi.roikremlin.perform.controller.PerformController.class);
        resources.add(com.roi.roikremlin.registerservice.controller.RegisterController.class);
    }
    
}
