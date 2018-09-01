package com.roi.roiauthenticationproxy.authentication.dao;

import com.roi.roiauthenticationproxy.authentication.UserDTO;
import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import javax.ejb.Local;

@Local
public interface UserDAO {
    Long save(UserDTO user) throws AuthenticationException;
    UserDTO update(UserDTO supplyOrder) throws AuthenticationException;
    void delete(Long id) throws AuthenticationException;
    UserDTO findUserById(Long id) throws AuthenticationException;
    UserDTO findUserByUsernameAndPassword(String username, String password) throws AuthenticationException;
}
