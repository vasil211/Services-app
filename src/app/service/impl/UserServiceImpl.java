package app.service.impl;

import app.dao.UserRepository;
import app.exeption.ConstraintViolationException;
import app.exeption.InvalidEntityDataException;
import app.exeption.InvalidUserLoginExeption;
import app.exeption.NonexistingEntityException;
import app.model.User;
import app.service.UserService;
import app.service.validators.UserValidation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserValidation userValidation;


    public UserServiceImpl(UserRepository userRepository, UserValidation userValidation) {
        this.userRepo = userRepository;
        this.userValidation = userValidation;
    }

    @Override
    public Collection<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @Override
    public List<User> getAllServiceProviders() {
        return userRepo.getAllServiceProviders();
    }

    @Override
    public List<User> getAllModerators() {
        return userRepo.getAllModerators();
    }

    @Override
    public List<User> getAllAdmins() {
        return userRepo.getAllAdmins();
    }

    @Override
    public String hash(String pass) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    @Override
    public User login(String username, String password) {
        try {
            userValidation.validatePassword(password);
        } catch (InvalidEntityDataException e) {
            System.out.println(e.getMessage());
            return null;
        }
        password = hash(password);
        User user;
        try {
            user = userRepo.login(username, password);
            return user;
        } catch (InvalidUserLoginExeption e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateLastOnline(Long id) {
        return userRepo.updateLastOnline(id);
    }

    @Override
    public User getUserById(Long id) throws NonexistingEntityException {
        User user = userRepo.findById(id);
        if (user == null) {
            throw new NonexistingEntityException("User with id: " + id + " does not exists. Try again");
        }
        return user;
    }


    @Override
    public User createUser(User user) {
        try {
            user = userValidation.validateUser(user);
            user.setCreated(LocalDateTime.now());
            user.setModified(LocalDateTime.now());
            user.setPassword(hash(user.getPassword()));
        } catch (ConstraintViolationException ex) {
            var sb = new StringBuilder(ex.getMessage());

                sb.append(", invalid fields:\n");
                var violations = (ex.getFieldViolations());
                sb.append(violations.stream().map(v -> String.format(" - %s.%s [%s] - %s",
                                v.getType().substring(v.getType().lastIndexOf(".") + 1),
                                v.getField(),
                                v.getInvalidValue(),
                                v.getErrorMessage())
                        ).collect(Collectors.joining("\n"))
                );

            System.out.println(sb);
            return null;
        }
        return userRepo.create(user);
    }

    @Override
    public User updateUser(User user, String filedUpdating, String newValue) {
        if (filedUpdating.equals("password")) {
            try {
                userValidation.validatePassword(newValue);
                user.setPassword(hash(newValue));
            } catch (InvalidEntityDataException e) {
                e.printStackTrace();
            }
        } else if (filedUpdating.equals("username")) {
            try {
                userValidation.validateUsername(newValue);
                user.setUserName(newValue);
            }catch (InvalidEntityDataException e) {
                e.printStackTrace();
            }
        } else if (filedUpdating.equals("email")) {
            try {
                userValidation.validateEmailAddress(newValue);
                user.setEmail(newValue);

            }catch (InvalidEntityDataException e) {
                e.printStackTrace();
            }
        } else if (filedUpdating.equals("firstName")) {
            try {
                userValidation.validateFirstName(newValue);
                user.setFirstName(newValue);
            }
            catch (InvalidEntityDataException e) {
                e.printStackTrace();
            }
        }else if (filedUpdating.equals("lastName")) {
            try {
                userValidation.validateLastName(newValue);
                user.setLastName(newValue);
            }catch (InvalidEntityDataException e) {
                e.printStackTrace();
            }
        }else if (filedUpdating.equals("phone")) {
            try {
                userValidation.validatePhone(newValue);
                user.setPhone(newValue);
            }catch (InvalidEntityDataException e) {
                e.printStackTrace();
            }
        }
        user.setModified(LocalDateTime.now());
        return userRepo.update(user);
    }

    @Override
    public String changePassword(Long id) {
        Scanner sc = new Scanner(System.in);
        String password;
        do {
            System.out.println("New password: ");
            password = sc.nextLine();
            try {
                userValidation.validatePassword(password);
            } catch (InvalidEntityDataException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again: ");
                continue;
            }
            break;
        } while (true);
        password = hash(password);
        userRepo.changePassword(password, id);
        return password;
    }

    @Override
    public boolean deleteUserById(Long id) throws NonexistingEntityException {
        if (!userRepo.deleteById(id)) {
            throw new NonexistingEntityException("User with id: " + id + " does not exist");
        }
        return true;
    }


    @Override
    public Long count() {
        return userRepo.count();
    }
}
