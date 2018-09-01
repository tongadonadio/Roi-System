package com.roi.roiauthenticationproxy.authentication.service;

import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.MessageHelper;
import com.roi.roiauthenticationproxy.authentication.UserDTO;
import com.roi.roiauthenticationproxy.authentication.dao.UserDAO;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthenticationServiceImpl implements AuthenticationService { 
    
    @EJB
    private UserDAO userDAO;
    
    @Inject
    private JwtHelper jwtHelper;
    
    @Inject
    private MessageHelper messages;
    
    @Override
    public String authenticate(UserDTO user) throws AuthenticationException {
        String token = "";
        UserDTO completeUser = userDAO.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(completeUser != null) {
            token = jwtHelper.encodeUser(completeUser);
        }
        return token;
    }
    
    @Override
    public Boolean validateUserToken(String token) throws AuthenticationException {
        UserDTO user = null;
        Long userId = jwtHelper.decodeId(token);
        if(userId != null) {
           user = userDAO.findUserById(userId);
        }
        if(user == null) {
            throw new AuthenticationException(messages.getMessage("authentication.user.token.error"));
        }
        return true;
    }
}
