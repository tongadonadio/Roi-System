package com.roi.roiauthenticationproxy.authentication.service;

import com.roi.roiauthenticationproxy.authentication.UserDTO;
import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import javax.ejb.Local;

@Local
public interface UserService {
    UserDTO getUser(Long id) throws AuthenticationException;
    Long createUser(UserDTO user) throws AuthenticationException;
    UserDTO updateUser(UserDTO user) throws AuthenticationException;
    void deleteUser(Long id) throws AuthenticationException;
    UserDTO getUserFromToken(String token) throws AuthenticationException;
}
