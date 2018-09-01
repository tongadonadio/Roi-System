
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rest_service")
public class RestService implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
        
    @Column(name = "url")
    private String url;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private RestMethod method;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "restService", cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<RestQueryParam> queryParams;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "restService", cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<RestBodyItem> body;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "restService", cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<RestPathParam> pathParams;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @ManyToOne
    @JoinTable(name="registered_application_rest_service_link")
    private RegisteredApplication registeredApplication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RestMethod getMethod() {
        return method;
    }

    public void setMethod(RestMethod method) {
        this.method = method;
    }

    public List<RestQueryParam> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<RestQueryParam> queryParams) {
        this.queryParams = queryParams;
    }

    public List<RestBodyItem> getBody() {
        return body;
    }

    public void setBody(List<RestBodyItem> body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<RestPathParam> getPathParams() {
        return pathParams;
    }

    public void setPathParams(List<RestPathParam> pathParams) {
        this.pathParams = pathParams;
    }

}
