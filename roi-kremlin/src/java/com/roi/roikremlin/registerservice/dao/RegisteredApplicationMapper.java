
package com.roi.roikremlin.registerservice.dao;

import com.roi.roikremlin.registerservice.ApplicationUtil;
import com.roi.roikremlin.registerservice.JmsServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisteredApplicationEx;
import com.roi.roikremlin.registerservice.ServiceItemDTO;
import com.roi.roikremlin.registerservice.RestServiceDTO;
import com.roi.roikremlin.registerservice.ServiceParamsEx;
import com.roi.roikremlin.registerservice.ServiceEx;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegisteredApplicationMapper {
    
    private static final String REST = "REST";
    private static final String JMS = "JMS";
    
    public static RegisteredApplication toEntity(final RegisterServiceDTO dto) {
        RegisteredApplication registeredApplication = new RegisteredApplication();
        registeredApplication.setApplicationName(dto.getApplicationName());
        registeredApplication.setType(ServiceType.valueOf(dto.getType().toUpperCase()));
        registeredApplication.setToken(dto.getToken());
        
        if(ApplicationUtil.isNotNullAndEmpty(dto.getRestServices())) {
            if(REST.equals(dto.getType())) {
                List<RestService> restServices = dto.getRestServices()
                    .stream()
                    .map(rs -> mapRestServiceEntity(rs, registeredApplication))
                    .collect(Collectors.toList());
                registeredApplication.setRestServices(restServices);
            }
        }
        
        if(ApplicationUtil.isNotNullAndEmpty(dto.getJmsServices())) {
            if(JMS.equals(dto.getType())) {
                List<JmsService> jmsServices = dto.getJmsServices()
                    .stream()
                    .map(jms -> mapJmsServiceEntity(jms, registeredApplication))
                    .collect(Collectors.toList());
                registeredApplication.setJmsServices(jmsServices);
            }
        }
        
        return registeredApplication;
    }
    
    private static JmsService mapJmsServiceEntity(final JmsServiceDTO dto, final RegisteredApplication registeredApplication) {
        JmsService jmsService = new JmsService();
        
        jmsService.setDescription(dto.getDescription());
        jmsService.setHost(dto.getHost());
        jmsService.setName(dto.getName());
        jmsService.setPort(dto.getPort());
        jmsService.setQueueName(dto.getQueueName());
        jmsService.setRegisteredApplication(registeredApplication);
        jmsService.setConnectionFactoryName(dto.getConnectionFactoryName());
        List<JmsBodyItem> body = dto.getBodyParams()
                .stream()
                .map(rbi -> mapRestBodyItemEntity(rbi, jmsService))
                .collect(Collectors.toList());
        jmsService.setBodyItems(body);
        
        return jmsService;
    }
    
    private static JmsBodyItem mapRestBodyItemEntity(final ServiceItemDTO dto, final JmsService jmsService) {
        JmsBodyItem jmsBodyItem = new JmsBodyItem();
        jmsBodyItem.setJmsService(jmsService);
        jmsBodyItem.setIsRequired(dto.getIsRequired());
        jmsBodyItem.setName(dto.getName());
        jmsBodyItem.setType(dto.getType());
        
        return jmsBodyItem;
    }
    
    private static RestService mapRestServiceEntity(final RestServiceDTO dto, final RegisteredApplication registeredApplication) {
        RestService restService = new RestService();
        restService.setRegisteredApplication(registeredApplication);
        restService.setUrl(dto.getUrl());
        restService.setName(dto.getName());
        restService.setDescription(dto.getDescription());
        restService.setMethod(RestMethod.valueOf(dto.getMethod().toUpperCase()));
        
        if(ApplicationUtil.isNotNullAndEmpty(dto.getBody())) {
            List<RestBodyItem> body = dto.getBody()
                .stream()
                .map(rbi -> mapRestBodyItemEntity(rbi, restService))
                .collect(Collectors.toList());
            restService.setBody(body);
        }
        
        if(ApplicationUtil.isNotNullAndEmpty(dto.getQueryParams())) {
            List<RestQueryParam> queryParams = dto.getQueryParams()
                .stream()
                .map(rbi -> mapRestQueryParamEntity(rbi, restService))
                .collect(Collectors.toList());
            restService.setQueryParams(queryParams);
        }
        
        if(ApplicationUtil.isNotNullAndEmpty(dto.getPathParams())) {
            List<RestPathParam> pathParams = dto.getPathParams()
                .stream()
                .map(rbi -> mapRestPathParamEntity(rbi, restService))
                .collect(Collectors.toList());
            restService.setPathParams(pathParams);
        }
        
        return restService;
    }
    
    private static RestBodyItem mapRestBodyItemEntity(final ServiceItemDTO dto, final RestService restService) {
        RestBodyItem restBodyItem = new RestBodyItem();
        restBodyItem.setRestService(restService);
        restBodyItem.setIsRequired(dto.getIsRequired());
        restBodyItem.setName(dto.getName());
        restBodyItem.setType(dto.getType());
        
        return restBodyItem;
    }
    
    private static RestQueryParam mapRestQueryParamEntity(final ServiceItemDTO dto, final RestService restService) {
        RestQueryParam restQueryParam = new RestQueryParam();
        restQueryParam.setRestService(restService);
        restQueryParam.setIsRequired(dto.getIsRequired());
        restQueryParam.setName(dto.getName());
        restQueryParam.setType(dto.getType());
        
        return restQueryParam;
    }
    
    private static RestPathParam mapRestPathParamEntity(final ServiceItemDTO dto, final RestService restService) {
        RestPathParam restPathParam = new RestPathParam();
        restPathParam.setRestService(restService);
        restPathParam.setIsRequired(dto.getIsRequired());
        restPathParam.setName(dto.getName());
        restPathParam.setType(dto.getType());
        
        return restPathParam;
    }

    public static RegisteredApplicationEx toExchange(final RegisteredApplication entity) {
        RegisteredApplicationEx registeredApplicationEx = new RegisteredApplicationEx();
        registeredApplicationEx.setId(entity.getId());
        registeredApplicationEx.setApplicationName(entity.getApplicationName());

        if(ApplicationUtil.isNotNullAndEmpty(entity.getRestServices())) {
            List<ServiceEx> servicesEx = entity.getRestServices()
                .stream()
                .map(rs -> mapServiceEx(rs))
                .collect(Collectors.toList());
            
            registeredApplicationEx.setServices(servicesEx);
        }
        
        if(ApplicationUtil.isNotNullAndEmpty(entity.getJmsServices())) {
            List<ServiceEx> servicesEx = entity.getJmsServices()
                .stream()
                .map(jms -> mapServiceEx(jms))
                .collect(Collectors.toList());
            
            registeredApplicationEx.setServices(servicesEx);
        }
        
        return registeredApplicationEx;
    }
    
    private static ServiceEx mapServiceEx(final JmsService jmsService) {
        ServiceEx serviceEx = new ServiceEx();
        
        serviceEx.setServiceName(jmsService.getName());
        serviceEx.setServiceDescription(jmsService.getDescription());
        final List<ServiceParamsEx> params = new ArrayList<>();
        
        if(ApplicationUtil.isNotNullAndEmpty(jmsService.getBodyItems())) {
            jmsService.getBodyItems().forEach((restBodyItem) -> {
                params.add(mapParamEx(restBodyItem));
            });
        }
        
        if(!params.isEmpty()) {
            serviceEx.setParams(params);
        }
        
        return serviceEx;
    }
    
    private static ServiceEx mapServiceEx(final RestService restService) {
        ServiceEx serviceEx = new ServiceEx();
        
        serviceEx.setServiceName(restService.getName());
        serviceEx.setServiceDescription(restService.getDescription());
        final List<ServiceParamsEx> params = new ArrayList<>();
        if(ApplicationUtil.isNotNullAndEmpty(restService.getBody())) {
            restService.getBody().forEach((restBodyItem) -> {
                params.add(mapParamEx(restBodyItem));
            });
        }
        
        if(ApplicationUtil.isNotNullAndEmpty(restService.getQueryParams())) {
            restService.getQueryParams().forEach((restQueryParam) -> {
                params.add(mapParamEx(restQueryParam));
            });
        }
        
        if(ApplicationUtil.isNotNullAndEmpty(restService.getPathParams())) {
            restService.getPathParams().forEach((restPathParam) -> {
                params.add(mapParamEx(restPathParam));
            });
        }
        
        if(!params.isEmpty()) {
            serviceEx.setParams(params);
        }
        
        return serviceEx;
    }
    
    private static ServiceParamsEx mapParamEx(final JmsBodyItem bodyItem) {
       ServiceParamsEx paramEx = new ServiceParamsEx();
       
       paramEx.setIsRequired(bodyItem.getIsRequired());
       paramEx.setName(bodyItem.getName());
       paramEx.setType(bodyItem.getType());
       
       return paramEx;
    }
    
    private static ServiceParamsEx mapParamEx(final RestQueryParam queryParam) {
       ServiceParamsEx paramEx = new ServiceParamsEx();
       
       paramEx.setIsRequired(queryParam.getIsRequired());
       paramEx.setName(queryParam.getName());
       paramEx.setType(queryParam.getType());
       
       return paramEx;
    }
    
    private static ServiceParamsEx mapParamEx(final RestBodyItem bodyItem) {
       ServiceParamsEx paramEx = new ServiceParamsEx();
       
       paramEx.setIsRequired(bodyItem.getIsRequired());
       paramEx.setName(bodyItem.getName());
       paramEx.setType(bodyItem.getType());
       
       return paramEx;
    }
    
    private static ServiceParamsEx mapParamEx(final RestPathParam pathParam) {
       ServiceParamsEx paramEx = new ServiceParamsEx();
       
       paramEx.setIsRequired(pathParam.getIsRequired());
       paramEx.setName(pathParam.getName());
       paramEx.setType(pathParam.getType());
       
       return paramEx;
    }
    
    public static RegisterServiceDTO toDTO(final RegisteredApplication entity) {
        RegisterServiceDTO registerServiceDTO = new RegisterServiceDTO();
        registerServiceDTO.setId(entity.getId());
        registerServiceDTO.setApplicationName(entity.getApplicationName());
        registerServiceDTO.setType(entity.getType().toString());
        registerServiceDTO.setToken(entity.getToken());

        if(ApplicationUtil.isNotNullAndEmpty(entity.getRestServices())) {
            List<RestServiceDTO> restServicesDTO = entity.getRestServices()
                .stream()
                .map(rs -> mapRestServiceDTO(rs))
                .collect(Collectors.toList());
            
            registerServiceDTO.setRestServices(restServicesDTO);
        }
        
        if(ApplicationUtil.isNotNullAndEmpty(entity.getJmsServices())) {
            List<JmsServiceDTO> jmsServicesDTO = entity.getJmsServices()
                .stream()
                .map(jms -> mapJmsServiceDTO(jms))
                .collect(Collectors.toList());
            
            registerServiceDTO.setJmsServices(jmsServicesDTO);
        }
        
        return registerServiceDTO;
    }
    
    private static JmsServiceDTO mapJmsServiceDTO(final JmsService jmsService) {
        JmsServiceDTO jmsServiceDTO = new JmsServiceDTO();
        
        jmsServiceDTO.setName(jmsService.getName());
        jmsServiceDTO.setDescription(jmsService.getDescription());
        jmsServiceDTO.setConnectionFactoryName(jmsService.getConnectionFactoryName());
        jmsServiceDTO.setPort(jmsService.getPort());
        jmsServiceDTO.setHost(jmsService.getHost());
        jmsServiceDTO.setQueueName(jmsService.getQueueName());
        
        List<ServiceItemDTO> body = jmsService.getBodyItems()
            .stream()
            .map(jms -> mapJmsItemDTO(jms))
            .collect(Collectors.toList());
        jmsServiceDTO.setBodyParams(body);
        
        return jmsServiceDTO;
    }
    
    private static ServiceItemDTO mapJmsItemDTO(final JmsBodyItem bodyItem) {
       ServiceItemDTO bodyItemDTO = new ServiceItemDTO();
       
       bodyItemDTO.setIsRequired(bodyItem.getIsRequired());
       bodyItemDTO.setName(bodyItem.getName());
       bodyItemDTO.setType(bodyItem.getType());
       
       return bodyItemDTO;
    }
    
    private static RestServiceDTO mapRestServiceDTO(final RestService restService) {
        RestServiceDTO restServiceDTO = new RestServiceDTO();
        
        restServiceDTO.setName(restService.getName());
        restServiceDTO.setDescription(restService.getDescription());
        restServiceDTO.setMethod(restService.getMethod().toString());
        restServiceDTO.setUrl(restService.getUrl());
        
        List<ServiceItemDTO> body = restService.getBody()
            .stream()
            .map(rbi -> mapRestItemDTO(rbi))
            .collect(Collectors.toList());
        restServiceDTO.setBody(body);
        
        List<ServiceItemDTO> queryParams = restService.getQueryParams()
            .stream()
            .map(rbi -> mapRestItemDTO(rbi))
            .collect(Collectors.toList());
        restServiceDTO.setQueryParams(queryParams);
        
        List<ServiceItemDTO> pathParams = restService.getPathParams()
            .stream()
            .map(rbi -> mapRestItemDTO(rbi))
            .collect(Collectors.toList());
        restServiceDTO.setPathParams(pathParams);
        
        return restServiceDTO;
    }
    
    private static ServiceItemDTO mapRestItemDTO(final RestQueryParam queryParam) {
       ServiceItemDTO restItemDTO = new ServiceItemDTO();
       
       restItemDTO.setIsRequired(queryParam.getIsRequired());
       restItemDTO.setName(queryParam.getName());
       restItemDTO.setType(queryParam.getType());
       
       return restItemDTO;
    }
    
    private static ServiceItemDTO mapRestItemDTO(final RestBodyItem bodyItem) {
       ServiceItemDTO restItemDTO = new ServiceItemDTO();
       
       restItemDTO.setIsRequired(bodyItem.getIsRequired());
       restItemDTO.setName(bodyItem.getName());
       restItemDTO.setType(bodyItem.getType());
       
       return restItemDTO;
    }
    
    private static ServiceItemDTO mapRestItemDTO(final RestPathParam pathParam) {
       ServiceItemDTO restItemDTO = new ServiceItemDTO();
       
       restItemDTO.setIsRequired(pathParam.getIsRequired());
       restItemDTO.setName(pathParam.getName());
       restItemDTO.setType(pathParam.getType());
       
       return restItemDTO;
    }
    
}