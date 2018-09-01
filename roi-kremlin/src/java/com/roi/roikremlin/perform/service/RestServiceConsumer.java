
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.perform.PerformParamDTO;
import com.roi.roikremlin.perform.PerformRequestDTO;
import com.roi.roikremlin.registerservice.ApplicationUtil;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceException;
import com.roi.roikremlin.registerservice.ServiceItemDTO;
import com.roi.roikremlin.registerservice.RestServiceDTO;
import com.roi.roikremlin.registerservice.dao.RestMethod;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RestServiceConsumer implements ServiceConsumer {

    @EJB
    private RestSerivceClient restServiceClient;
    
    @Inject
    private TypeMapper typeMapper;
    
    @Override
    public String consumeService(PerformRequestDTO performRequest, RegisterServiceDTO registerServiceDTO) throws RegisterServiceException {
        RestServiceDTO restService = registerServiceDTO.getRestServices()
                .stream()
                .filter(rs -> rs.getName().equals(performRequest.getServiceName()))
                .findFirst().get();
        
        if(ApplicationUtil.isNotNullAndEmpty(performRequest.getParams())) {
            Map<String, PerformParamDTO> performRequestParams = performRequest.getParams()
                    .stream()
                    .collect(Collectors.toMap(PerformParamDTO::getName, p -> p));

            List<PerformParamDTO> pathParams = getParams(restService.getPathParams(), performRequestParams);
            List<PerformParamDTO> queryParams = getParams(restService.getQueryParams(), performRequestParams);     

            String url = addPathParams(restService.getUrl(), pathParams);

            Map<String, String> queryParamsMap = queryParams
                    .stream()
                    .collect(Collectors.toMap(PerformParamDTO::getName, PerformParamDTO::getValue));

            Map<String, Object> bodyParams = new HashMap();
            restService.getBody()
                    .stream()
                    .filter(p -> performRequestParams.containsKey(p.getName()))
                    .map(p -> bodyParams.put(p.getName(), typeMapper.castObjectType(p.getType(),performRequestParams.get(p.getName()).getValue())));

            return restServiceClient.callService(url, RestMethod.valueOf(restService.getMethod()), queryParamsMap, bodyParams, registerServiceDTO.getToken());
        } else {
            return restServiceClient.callService(restService.getUrl(), RestMethod.valueOf(restService.getMethod()), null, null, registerServiceDTO.getToken());
        }
    }
    
    private List<PerformParamDTO> getParams(final List<ServiceItemDTO> serviceParams, final Map<String, PerformParamDTO> performParams) {
        return serviceParams
                .stream()
                .map(restItem -> performParams.get(restItem.getName()))
                .collect(Collectors.toList());
    }
        
    private String addPathParams(final String url, final List<PerformParamDTO> params) {
        String resultUrl = url;
        for(PerformParamDTO p : params) {
            resultUrl = resultUrl.replaceFirst(getReplaceableParam(p), p.getValue());
        }
        return resultUrl;
    }
    
    private String getReplaceableParam(PerformParamDTO param) {
        String p = "\\{" + param.getName() + "\\}";
        return p;
    }
 }
