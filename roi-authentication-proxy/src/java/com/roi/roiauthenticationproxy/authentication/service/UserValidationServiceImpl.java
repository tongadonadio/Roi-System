package com.roi.roiauthenticationproxy.authentication.service;

import com.roi.roiauthenticationproxy.authentication.MessageHelper;
import com.roi.roiauthenticationproxy.authentication.UserDTO;
import com.roi.roiauthenticationproxy.authentication.userRole;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserValidationServiceImpl implements UserValidationService {

    @Inject
    private MessageHelper messages;
    
    @Override
    public List<String> validate(UserDTO user) {
        return validateUsernamePassword(user);
    }
    
    @Override
    public String validateAdminUser(UserDTO user) {
        String message = null;
        if(!user.getRole().equals(userRole.ADMIN)) {
            message = messages.getMessage("authentication.user.permission.error");
        }
        return message;
    }
            
    private List<String> validateUsernamePassword(UserDTO user) {
        List<String> errorMessages = new ArrayList();
        validateNotNull(user.getUsername(), "username", errorMessages);
        validateNotNull(user.getPassword(), "password", errorMessages);
        return errorMessages;
    }
    
    private <T> void validateNotNull(T value, String fieldName, List<String> errorMessages) {
        if (value == null) {
            errorMessages.add(messages.getMessage("authentication.validation.error", fieldName));
        }
    }

}
