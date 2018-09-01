
package com.roi.roikremlin.perform.service;

import com.roi.roikremlin.perform.PerformParamDTO;
import com.roi.roikremlin.perform.PerformRequestDTO;
import com.roi.roikremlin.registerservice.JmsServiceDTO;
import com.roi.roikremlin.registerservice.RegisterServiceDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class JmsServiceConsumer implements ServiceConsumer {
    
    @Inject
    private TypeMapper typeMapper;
    
    @EJB
    private JmsServiceClient jmsServiceClient;

    @Override
    public String consumeService(PerformRequestDTO performRequest, RegisterServiceDTO registerServiceDTO) {
        JmsServiceDTO jmsService = registerServiceDTO.getJmsServices()
                .stream()
                .filter(rs -> rs.getName().equals(performRequest.getServiceName()))
                .findFirst().get();
        
        Map<String, PerformParamDTO> performRequestParams = performRequest.getParams()
                .stream()
                .collect(Collectors.toMap(PerformParamDTO::getName, p -> p));
        
        Map<String, Object> bodyParams = new HashMap();
        jmsService.getBodyParams()
                .stream()
                .filter(p -> performRequestParams.containsKey(p.getName()))
                .map(p -> bodyParams.put(p.getName(), typeMapper.castObjectType(p.getType(),performRequestParams.get(p.getName()).getValue())));
        
        return jmsServiceClient.callService(jmsService.getHost(), jmsService.getPort(), jmsService.getConnectionFactoryName(), jmsService.getQueueName(), bodyParams);
    }
    
}
