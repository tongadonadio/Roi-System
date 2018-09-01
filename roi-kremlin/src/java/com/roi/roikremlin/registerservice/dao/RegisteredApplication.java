
package com.roi.roikremlin.registerservice.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "registered_application")
public class RegisteredApplication implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ServiceType type;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "registeredApplication", cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<RestService> restServices; 
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "registeredApplication", cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<JmsService> jmsServices;
    
    @Column(name = "token")
    private String token;
    
    @Column(name = "application_name")
    private String applicationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public List<RestService> getRestServices() {
        return restServices;
    }

    public void setRestServices(List<RestService> restServices) {
        this.restServices = restServices;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<JmsService> getJmsServices() {
        return jmsServices;
    }

    public void setJmsServices(List<JmsService> jmsServices) {
        this.jmsServices = jmsServices;
    }
    
}
