package com.roi.roiauthenticationproxy.authentication.service;

import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.UserDTO;
import javax.ejb.Local;

@Local
public interface AuthenticationService {
    String authenticate(UserDTO user) throws AuthenticationException;
    Boolean validateUserToken(String token) throws AuthenticationException;
}
