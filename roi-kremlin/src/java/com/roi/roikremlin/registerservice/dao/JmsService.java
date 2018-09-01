
package com.roi.roikremlin.registerservice.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "jms_service")
public class JmsService implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "host")
    private String host;
    
    @Column(name = "port")
    private Integer port;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "queue_name")
    private String queueName;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "connection_factory_name")
    private String connectionFactoryName;
    
    @ManyToOne
    @JoinTable(name="registered_application_jms_service_link")
    private RegisteredApplication registeredApplication;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "jmsService", cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<JmsBodyItem> bodyItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RegisteredApplication getRegisteredApplication() {
        return registeredApplication;
    }

    public void setRegisteredApplication(RegisteredApplication registeredApplication) {
        this.registeredApplication = registeredApplication;
    }

    public List<JmsBodyItem> getBodyItems() {
        return bodyItems;
    }

    public void setBodyItems(List<JmsBodyItem> bodyItems) {
        this.bodyItems = bodyItems;
    }

    public String getConnectionFactoryName() {
        return connectionFactoryName;
    }

    public void setConnectionFactoryName(String connectionFactoryName) {
        this.connectionFactoryName = connectionFactoryName;
    }
    
}
