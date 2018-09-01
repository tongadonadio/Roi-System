
package com.roi.roikremlin.registerservice.service;

import com.roi.roikremlin.registerservice.ApplicationUtil;
import com.roi.roikremlin.registerservice.JmsServiceDTO;
import com.roi.roikremlin.registerservice.MessageHelper;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.ServiceItemDTO;
import com.roi.roikremlin.registerservice.RestServiceDTO;
import com.roi.roikremlin.registerservice.dao.RegisterServiceDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RegisterValidationServiceImpl implements RegisterValidationService {
    
    @Inject
    private MessageHelper messages;
    
    @EJB
    private RegisterServiceDAO registerServiceDAO;
    
    private static final String VALIDATION_EMPTY_ERROR_MESSAGE = "registerservice.validation.empty.error";
    private static final String VALIDATION_FORMAT_ERROR_MESSAGE = "registerservice.validation.format.error";
    private static final String VALIDATION_WORDS_AMOUNT_ERROR_MESSAGE = "registerservice.validation.words.amount.error";
    private static final String VALIDATION_SERVICE_NAME_NOT_UNIQUE_MESSAGE = "registerservice.validation.service.not.unique.error";
    private static final String VALIDATION_SERVICE_PATH_PARAM_ERROR_MESSAGE = "registerservice.validation.service.path.param.error";
    private static final String VALIDATION_EMPTY_SERVICES_ERROR_MESSAGE = "registerservice.validation.services.empty";
    private static final List<String> VALID_SERVICE_TYPES = new ArrayList<>();
    private static final List<String> VALID_REST_METHODS = new ArrayList<>();
    private static final List<String> VALID_VALUE_BODY_TYPES = new ArrayList<>();
    private static final List<String> VALID_VALUE_QUERY_AND_PARAM_TYPES = new ArrayList<>();
    private static final String URL_PATTERN = "https?:\\/\\/(www\\.)?((\\{[-a-zA-Z0-9@:%._\\+~#=]+\\})([-a-zA-Z0-9@:%._\\+~#=]+)(\\/))+";
    private static final String REST = "REST";
    private static final String JMS = "JMS";
    
    static {
        VALID_SERVICE_TYPES.add(REST);
        VALID_SERVICE_TYPES.add(JMS);
        VALID_SERVICE_TYPES.add("REMOTE");
        
        VALID_REST_METHODS.add("GET");
        VALID_REST_METHODS.add("POST");
        VALID_REST_METHODS.add("PUT");
        VALID_REST_METHODS.add("DELETE");
        
        VALID_VALUE_BODY_TYPES.add("string");
        VALID_VALUE_BODY_TYPES.add("integer");
        VALID_VALUE_BODY_TYPES.add("boolean");
        VALID_VALUE_BODY_TYPES.add("double");
        VALID_VALUE_BODY_TYPES.add("int");
        VALID_VALUE_BODY_TYPES.add("list<string>");
        VALID_VALUE_BODY_TYPES.add("list<integer>");
        VALID_VALUE_BODY_TYPES.add("list<double>");
        
        VALID_VALUE_QUERY_AND_PARAM_TYPES.add("string");
        VALID_VALUE_QUERY_AND_PARAM_TYPES.add("integer");
        VALID_VALUE_QUERY_AND_PARAM_TYPES.add("boolean");
        VALID_VALUE_QUERY_AND_PARAM_TYPES.add("double");
        VALID_VALUE_QUERY_AND_PARAM_TYPES.add("int");
    }

    @Override
    public List<String> validate(final RegisterServiceDTO registerService) throws RegisterServiceException {
        List<String> errorMessages = new ArrayList<>();
        validateNotNull(registerService.getType(), "type", errorMessages);
        validateNotNull(registerService.getToken(), "token", errorMessages);
        validateNotNull(registerService.getApplicationName(), "applicationName", errorMessages);
        validateTypeName(registerService.getType(), errorMessages);
        
        if(registerService.getType() != null && REST.equals(registerService.getType())) {
            if(ApplicationUtil.isNotNullAndEmpty(registerService.getRestServices())) {
                for(RestServiceDTO registerServiceDTO : registerService.getRestServices()) {
                    validateRestService(registerServiceDTO, errorMessages);
                }
            } else {
                errorMessages.add(messages.getMessage(VALIDATION_EMPTY_SERVICES_ERROR_MESSAGE, REST));
            }
        }
        
        if(registerService.getType() != null && JMS.equals(registerService.getType())) {
            if(ApplicationUtil.isNotNullAndEmpty(registerService.getJmsServices())) {
                for(JmsServiceDTO jmsServiceDTO : registerService.getJmsServices()) {
                    validateJmsService(jmsServiceDTO, errorMessages);
                }
            } else {
                errorMessages.add(messages.getMessage(VALIDATION_EMPTY_SERVICES_ERROR_MESSAGE, JMS));
            }
        }
        
        return errorMessages;
    }
    
    private void validateJmsService(final JmsServiceDTO jmsService, final List<String> errorMessages) throws RegisterServiceException {
        validateNotNull(jmsService.getHost(), "jms-service-host", errorMessages);
        validateNotNull(jmsService.getPort(), "jms-service-port", errorMessages);
        validateNotNull(jmsService.getName(), "jms-service-name", errorMessages);
        validateNotNull(jmsService.getQueueName(), "jms-service-queue-name", errorMessages);
        validateNotNull(jmsService.getDescription(), "jms-service-description", errorMessages);
        validateNotNull(jmsService.getBodyParams(), "jms-service-params", errorMessages);

        if(ApplicationUtil.isNotNullAndEmpty(jmsService.getName())) {
            validateWordsLenght(jmsService.getName(), "jms-service-name", errorMessages);
            validateServiceNameUnique(jmsService.getName(), errorMessages);
        }
                
        if(ApplicationUtil.isNotNullAndEmpty(jmsService.getBodyParams())) {
            jmsService.getBodyParams().stream().forEach(bodyItem -> validateRestItem("body", bodyItem , errorMessages));
        }
    }
    
    private void validateRestService(final RestServiceDTO restService, final List<String> errorMessages) throws RegisterServiceException {
        validateNotNull(restService.getMethod(), "rest-service-method", errorMessages);
        validateNotNull(restService.getUrl(), "rest-service-url", errorMessages);
        validateNotNull(restService.getName(), "rest-service-name", errorMessages);
        validateNotNull(restService.getDescription(), "rest-service-description", errorMessages);
        if(ApplicationUtil.isNotNullAndEmpty(restService.getName())) {
            validateWordsLenght(restService.getName(), "rest-service-name", errorMessages);
            validateServiceNameUnique(restService.getName(), errorMessages);
        }
        if(ApplicationUtil.isNotNullAndEmpty(restService.getMethod())) {
            validateRestMethodName(restService.getMethod(), errorMessages);
        }
        if(ApplicationUtil.isNotNullAndEmpty(restService.getUrl())) {
        //    validateUrlFormat(restService.getUrl(), errorMessages);
            validateUrlParams(restService.getUrl(), restService.getPathParams(), errorMessages);
        }
        
        if(ApplicationUtil.isNotNullAndEmpty(restService.getBody())) {
            restService.getBody().stream().forEach(bodyItem -> validateRestItem("body", bodyItem , errorMessages));
            restService.getBody().stream().forEach(paramItem -> validateBodyParam("query-param", paramItem , errorMessages));
        }
        if(ApplicationUtil.isNotNullAndEmpty(restService.getQueryParams())) {
            restService.getQueryParams().stream().forEach(paramItem -> validateRestItem("query-param", paramItem , errorMessages));
            restService.getQueryParams().stream().forEach(paramItem -> validatePathOrQueryParam("query-param", paramItem , errorMessages));
        }
        if(ApplicationUtil.isNotNullAndEmpty(restService.getPathParams())) {
            restService.getPathParams().stream().forEach(paramItem -> validateRestItem("path-param", paramItem , errorMessages));
            restService.getPathParams().stream().forEach(paramItem -> validatePathOrQueryParam("path-param", paramItem , errorMessages));
        }
    }
    
    private void validateRestItem(final String kind, final ServiceItemDTO item, final List<String> errorMessages) {
        StringBuilder sb = new StringBuilder();
        sb.append("rest-service-");
        sb.append(kind);
        sb.append("-");
        
        validateNotNull(item.getName(), sb.toString() + "name", errorMessages);
        validateNotNull(item.getType(), sb.toString() + "type", errorMessages);
        validateNotNull(item.getIsRequired(), sb.toString() + "isRequired", errorMessages);
    }
    
    private <T> void validateNotNull(final T value, final String fieldName, final List<String> errorMessages) {
        if (value == null) {
            errorMessages.add(messages.getMessage(VALIDATION_EMPTY_ERROR_MESSAGE, fieldName));
        }
    }
    
    private void validateUrlFormat(final String value, final List<String> errorMessages) {
        Pattern p = Pattern.compile(URL_PATTERN);
        Matcher m = p.matcher(value);
        if(!m.find()) {
            errorMessages.add(messages.getMessage(VALIDATION_FORMAT_ERROR_MESSAGE, "url"));
        }
    }
    
    private void validateRestMethodName(final String value, final List<String> errorMessages) {
        if(!VALID_REST_METHODS.stream().anyMatch(t -> t.equalsIgnoreCase(value))) {
            errorMessages.add(messages.getMessage(VALIDATION_FORMAT_ERROR_MESSAGE, "rest-service-method"));
        }
    }
    
    private void validateTypeName(final String value, final List<String> errorMessages) {
        if(!VALID_SERVICE_TYPES.stream().anyMatch(t -> t.equalsIgnoreCase(value))) {
            errorMessages.add(messages.getMessage(VALIDATION_FORMAT_ERROR_MESSAGE, "type"));
        }
    }
    
    private void validateWordsLenght(final String value, final String fieldName, final List<String> errorMessages) {
        String noSpacesValue = value.replaceAll("\\s+","");
        if(!value.equals(noSpacesValue)) {
            errorMessages.add(messages.getMessage(VALIDATION_WORDS_AMOUNT_ERROR_MESSAGE, fieldName));
        }
    }
    
    private void validateServiceNameUnique(final String serviceName, final List<String> errorMessages) throws RegisterServiceException {
        RegisterServiceDTO registerServiceDTO = this.registerServiceDAO.getRegisteredApplicationByServiceName(serviceName);
        if(registerServiceDTO != null) {
            errorMessages.add(messages.getMessage(VALIDATION_SERVICE_NAME_NOT_UNIQUE_MESSAGE, serviceName));
        }
    }
    
    private void validateUrlParams(final String url, final List<ServiceItemDTO> pathParams, final List<String> errorMessages) {
        String[] arrayParams = url.split("\\{");
        List<String> params = Arrays.asList(arrayParams);
        if(ApplicationUtil.isNotNullAndEmpty(params) && params.size() > 1 && ApplicationUtil.isNotNullAndEmpty(pathParams)) {
            List<ServiceItemDTO> effectiveParams = pathParams.stream().filter(p -> isParamInList(p, params)).collect(Collectors.toList());
            if(effectiveParams.size() != pathParams.size()) {
                errorMessages.add(messages.getMessage(VALIDATION_SERVICE_PATH_PARAM_ERROR_MESSAGE));
            }
        } else if(ApplicationUtil.isNotNullAndEmpty(params) && params.size() > 1 && !ApplicationUtil.isNotNullAndEmpty(pathParams)){
            errorMessages.add(messages.getMessage(VALIDATION_SERVICE_PATH_PARAM_ERROR_MESSAGE)); 
        }
    }
    
    private boolean isParamInList(final ServiceItemDTO param, final List<String> params) {
        boolean isInList = false;
        for(String p : params) {
            if(param != null && param.getName() != null) {
                isInList = isInList || p.contains((param.getName() + "}"));
            }
        }
        return isInList;
    }
    
    private void validatePathOrQueryParam(final String name, final ServiceItemDTO param, final List<String> errorMessages) {
        if(!VALID_VALUE_QUERY_AND_PARAM_TYPES.contains(param.getType().toLowerCase())) {
            errorMessages.add(messages.getMessage(VALIDATION_FORMAT_ERROR_MESSAGE, name));
        }
    }
    
    private void validateBodyParam(final String name, final ServiceItemDTO param, final List<String> errorMessages) {
        if(!VALID_VALUE_BODY_TYPES.contains(param.getType().toLowerCase())) {
            errorMessages.add(messages.getMessage(VALIDATION_FORMAT_ERROR_MESSAGE, name));
        }
    }
    
}
