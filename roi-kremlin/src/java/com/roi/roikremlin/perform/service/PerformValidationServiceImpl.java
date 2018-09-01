
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.perform.PerformParamDTO;
import com.roi.roikremlin.perform.PerformRequestDTO;
import com.roi.roikremlin.registerservice.ApplicationUtil;
import com.roi.roikremlin.registerservice.MessageHelper;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.ServiceItemDTO;
import com.roi.roikremlin.registerservice.RestServiceDTO;
import com.roi.roikremlin.registerservice.dao.RegisterServiceDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PerformValidationServiceImpl implements PerformValidationService{
    
    @EJB
    private RegisterServiceDAO registerServiceDAO;
    
    @Inject
    private MessageHelper messages;
    
    @Inject
    private TypeMapper typeMapper;
    
    private static final String REST = "REST";
    private static final String VALIDATION_EMPTY_ERROR_MESSAGE = "registerservice.validation.empty.error";
    private static final String VALIDATION_FORMAT_ERROR_MESSAGE = "registerservice.validation.format.error";
    private static final String VALIDATION_SERVICE_DOES_NOT_EXIST = "perform.validation.service.does.not.exist";
    private static final String VALIDATION_FIELD_REQUIRED = "perform.validation.field.required";

    @Override
    public List<String> validate(PerformRequestDTO performRequest) throws RegisterServiceException {
        List<String> errorMessages = new ArrayList<>();
        
        validateNotNull(performRequest, "request body", errorMessages);
        if(errorMessages.size() <= 0) {
            validateNotNull(performRequest.getServiceName(), "serviceName", errorMessages);     
            if(performRequest.getServiceName() != null) {
                RegisterServiceDTO registerService = this.registerServiceDAO.getRegisteredApplicationByServiceName(performRequest.getServiceName());
            
                if(registerService == null) {
                    errorMessages.add(messages.getMessage(VALIDATION_SERVICE_DOES_NOT_EXIST, performRequest.getServiceName())); 
                }
                
                if(REST.equals(registerService.getType())) {
                    RestServiceDTO restService = registerService.getRestServices()
                            .stream()
                            .filter(rs -> rs.getName().equals(performRequest.getServiceName()))
                            .findFirst().get();
                    
                    Map<String, String> paramsMap = null;
                    
                    List<ServiceItemDTO> bodyParams = restService.getBody();
                    List<ServiceItemDTO> queryParams = restService.getQueryParams();
                    List<ServiceItemDTO> pathParams = restService.getPathParams();

                    if((bodyParams != null && bodyParams.size() > 0) || (queryParams != null && queryParams.size() > 0) || (pathParams != null && pathParams.size() > 0)) {
                        validateNotNull(performRequest.getParams(), "params", errorMessages);
                    }
                    
                    if(errorMessages.size() <= 0) {
                        if(ApplicationUtil.isNotNullAndEmpty(performRequest.getParams())) {
                            paramsMap = performRequest.getParams()
                                        .stream()
                                        .collect(Collectors.toMap(PerformParamDTO::getName, PerformParamDTO::getValue));
                        }
                    }
                    
                    
                    if(paramsMap != null && bodyParams != null && bodyParams.size() > 0) {
                        for(ServiceItemDTO bodyParam : bodyParams) {
                            String param = paramsMap.get(bodyParam.getName());
                            if(param == null && bodyParam.getIsRequired()) {
                                errorMessages.add(messages.getMessage(VALIDATION_FIELD_REQUIRED, bodyParam.getName()));
                            } else if(param != null) {
                                validateType(bodyParam.getName(), param, bodyParam.getType(), errorMessages);
                            }
                        }
                    }
                    
                    if(paramsMap != null && queryParams != null && queryParams.size() > 0) {
                        for(ServiceItemDTO queryParam : queryParams) {
                            String param = paramsMap.get(queryParam.getName());
                            if(param == null && queryParam.getIsRequired()) {
                                errorMessages.add(messages.getMessage(VALIDATION_FIELD_REQUIRED, queryParam.getName()));
                            } else if(param != null) {
                                validateType(queryParam.getName(), param, queryParam.getType(), errorMessages);
                            }
                        }
                    }
                    
                    if(paramsMap != null && pathParams != null && pathParams.size() > 0) {
                        for(ServiceItemDTO pathParam : pathParams) {
                            String param = paramsMap.get(pathParam.getName());
                            if(param == null && pathParam.getIsRequired()) {
                                errorMessages.add(messages.getMessage(VALIDATION_FIELD_REQUIRED, pathParam.getName()));
                            } else if(param != null) {
                                validateType(pathParam.getName(), param, pathParam.getType(), errorMessages);
                            }
                        }
                    }
                }
            }
        }
        
        return errorMessages;
    }  
    
    private void validateType(final String name, final String value, final String type, final List<String> errorMessages) {
        ParamTypes option = ParamTypes.getFromValue(type.toLowerCase());
        
        ParamTypes[] formatTypes = { ParamTypes.BOOLEAN, ParamTypes.INTEGER, ParamTypes.DOUBLE, ParamTypes.INT };
        ParamTypes[] listTypes = { ParamTypes.LIST_STRING, ParamTypes.LIST_INTEGER, ParamTypes.LIST_DOUBLE };
        
        if(Arrays.asList(formatTypes).contains(option)) {
            validateFormat(name, value, type.toLowerCase(), errorMessages);
        } else if(Arrays.asList(listTypes).contains(option)){
            validateList(name, value, type.toLowerCase(), errorMessages);
        } else if(option != ParamTypes.STRING) {
           errorMessages.add(messages.getMessage(VALIDATION_FORMAT_ERROR_MESSAGE, name));
        }
    }
    
    private void validateFormat(final String name, final String value, final String type, final List<String> errorMessages) {
         Object object = typeMapper.castObjectType(type.toLowerCase(), value);    
         if(object == null) {
             errorMessages.add(messages.getMessage(VALIDATION_FORMAT_ERROR_MESSAGE, name)); 
         }
    }
        
    private void validateList(final String name, final String value, final String type, final List<String> errorMessages) {
        List<?> list = typeMapper.castObjectType(type.toLowerCase(), value);
        if(ApplicationUtil.isNotNullAndEmpty(list)) {
            errorMessages.add(messages.getMessage(VALIDATION_FORMAT_ERROR_MESSAGE, name)); 
        }
    }
        
    private <T> void validateNotNull(final T value, final String fieldName, final List<String> errorMessages) {
        if (value == null) {
            errorMessages.add(messages.getMessage(VALIDATION_EMPTY_ERROR_MESSAGE, fieldName));
        }
    }
    
}
