package com.roi.roiauthenticationproxy.authentication.controller;

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
        resources.add(com.roi.roiauthenticationproxy.authentication.controller.AuthenticationController.class);
        resources.add(com.roi.roiauthenticationproxy.authentication.controller.AuthenticationExceptionMapper.class);
        resources.add(com.roi.roiauthenticationproxy.authentication.controller.UserController.class);
        resources.add(com.roi.roiauthenticationproxy.authentication.filter.JwtTokenFilter.class);
        resources.add(com.roi.roiauthenticationproxy.perform.controller.PerformController.class);
        resources.add(com.roi.roiauthenticationproxy.register.controller.RegisterController.class);
    }
    
}
