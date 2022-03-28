package app.dao;

import app.exeption.InvalidUserLoginExeption;
import app.model.User;

import java.util.List;

public interface UserRepository extends Repository<Long, User>{
    boolean changePassword(String password, Long id);
    boolean findUsername(String username);
    User login(String userName, String password) throws InvalidUserLoginExeption;
    boolean findEmail(String email);
    boolean updateLastOnline(Long id);
    List<User> getAllUsers();
    List<User> getAllServiceProviders();
    List<User> getAllModerators();
    List<User> getAllAdmins();

}
