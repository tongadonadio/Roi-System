package com.roi.roiauthenticationproxy.authentication.service;

import com.roi.roiauthenticationproxy.authentication.UserDTO;
import com.roi.roiauthenticationproxy.authentication.dao.UserDAO;
import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserServiceImpl implements UserService {

    @EJB
    private UserDAO userDAO;
    
    @Inject
    private JwtHelper jwtHelper;
    
    @Override
    public UserDTO getUser(Long id) throws AuthenticationException {
        return userDAO.findUserById(id);
    }

    @Override
    public Long createUser(UserDTO user) throws AuthenticationException {
        return userDAO.save(user);
    }

    @Override
    public UserDTO updateUser(UserDTO user) throws AuthenticationException {
        return userDAO.update(user);
    }

    @Override
    public void deleteUser(Long id) throws AuthenticationException {
        userDAO.delete(id);
    }
    
    @Override
    public UserDTO getUserFromToken(String token) throws AuthenticationException {
        UserDTO user = null;
        Long userId = jwtHelper.decodeId(token);
        if(userId != null) {
            user = userDAO.findUserById(userId);
        }
        return user;
    }
 }
