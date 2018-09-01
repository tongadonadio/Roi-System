package com.roi.roiauthenticationproxy.authentication.dao;

import com.roi.roiauthenticationproxy.authentication.UserDTO;
import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.MessageHelper;
import com.roi.roiauthenticationproxy.authentication.RoiLogger;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "roi-authentication-proxyPU")
    private EntityManager em;

    @Inject
    private RoiLogger logger;

    @Inject
    private MessageHelper messages;
    
    @Override
    public Long save(UserDTO user) throws AuthenticationException {
        User entity = UserMapper.toEntity(user);
        Long id = null;
        try {
            if(findUserByUsername(user.getUsername()) == null) {
               em.persist(entity);
               id = entity.getId();
            } else {
                throw new AuthenticationException(messages.getMessage("authentication.user.userexists.error", user.getUsername()));
            }
        } catch(IllegalArgumentException|TransactionRequiredException|EntityExistsException e) {
            logger.error(e.getMessage(), e, this.getClass());
            throw new AuthenticationException(messages.getMessage("authentication.user.save.error", user.getUsername()));
        }
        return id;
    }

    @Override
    public UserDTO update(UserDTO user) throws AuthenticationException {
        try {
            User entity = UserMapper.toEntity(user);
            if(findUserById(entity.getId()) != null) {
               em.merge(entity);
            }
            return UserMapper.toDTO(entity);
        } catch(IllegalArgumentException|TransactionRequiredException e) {
            logger.error(e.getMessage(), e, this.getClass());
            throw new AuthenticationException(messages.getMessage("authentication.user.update.error", user.getUsername()));
        }    
    }

    @Override
    public void delete(Long id) throws AuthenticationException {
        try {
            User user = findById(id);
            if(user != null) {
                em.remove(user);
            }
        } catch(IllegalArgumentException|TransactionRequiredException e) {
            logger.error(e.getMessage(), this.getClass());
            throw new AuthenticationException(messages.getMessage("authentication.user.delete.error", id.toString()));
        }
    }

    @Override
    public UserDTO findUserById(Long id) throws AuthenticationException {
        try {
            User user = findById(id);
            return user != null ? UserMapper.toDTO(user) : null;
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage(), e, this.getClass());
            throw new AuthenticationException(messages.getMessage("authentication.user.find.error", id.toString()));
        }
    }
    
    @Override
    public UserDTO findUserByUsernameAndPassword(String username, String password) throws AuthenticationException {
        List<User> users = null;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery query = cb.createQuery(User.class);
            Root<User> user = query.from(User.class);
            Predicate predicate = cb.and(cb.equal(user.get("username"), username), cb.equal(user.get("password"), password));
            query.select(user);
            query.where(predicate);
            users = em.createQuery(query).getResultList();
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage(), e, this.getClass());
            throw new AuthenticationException(messages.getMessage("authentication.user.findbyusername.error"));
        }
        return users.isEmpty() ? null : UserMapper.toDTO(users.get(0));
    }

    private UserDTO findUserByUsername(String username) throws IllegalArgumentException {
        List<User> users = null;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);
        query.where(cb.equal(user.get("username"), username));
        users = em.createQuery(query).getResultList();
        return users.isEmpty() ? null : UserMapper.toDTO(users.get(0));
    }
    
    private User findById(Long id){
        return em.find(User.class, id);
    }

}
