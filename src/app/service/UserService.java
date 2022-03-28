package app.service;


import app.exeption.InvalidEntityDataException;
import app.exeption.InvalidUserLoginExeption;
import app.exeption.NonexistingEntityException;
import app.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getAll();
    Collection<User> getAllUsers();
    Collection<User> getAllServiceProviders();
    Collection<User> getAllModerators();
    Collection<User> getAllAdmins();
    User login(String username, String password) ;
    User getUserById(Long id) throws NonexistingEntityException;
    User createUser(User user) ;
    User updateUser(User user);
    boolean deleteUserById(Long id) throws NonexistingEntityException;
    String hash(String pass);
    String changePassword(Long id);
    boolean updateLastOnline(Long id);
    Long count();
}
