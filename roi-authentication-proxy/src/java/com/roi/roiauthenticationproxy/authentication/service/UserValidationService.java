package com.roi.roiauthenticationproxy.authentication.service;

import com.roi.roiauthenticationproxy.authentication.UserDTO;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserValidationService {
    List<String> validate(UserDTO user);
    String validateAdminUser(UserDTO user);
}
