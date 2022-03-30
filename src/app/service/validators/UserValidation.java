package app.service.validators;

import app.dao.UserRepository;
import app.exeption.ConstraintViolation;
import app.exeption.ConstraintViolationException;
import app.exeption.InvalidEntityDataException;
import app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {

    private final UserRepository userRepo;

    public UserValidation(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    public User validateUser(User user) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();
        var firstName = user.getFirstName().trim().length();
        var lastName = user.getLastName().trim().length();
        if (firstName < 2 || firstName > 15) {
            violations.add(
                    new ConstraintViolation(user.getClass().getName(), "First Name", user.getFirstName(),
                            "First Name length should be between 2 and 15 characters."));
        }
        if (lastName < 2 || lastName > 15) {
            violations.add(
                    new ConstraintViolation(user.getClass().getName(), "Last Name", user.getLastName(),
                            "Last Name length should be between 2 and 15 characters."));
        }
        try {
            isValidEmailAddress(user.getEmail());
        } catch (InvalidEntityDataException e) {
            violations.add(
                    new ConstraintViolation(user.getClass().getName(), "Email", user.getEmail(),
                            e.getMessage()));
        }
        try {
            isValidUsername(user.getUserName());
        } catch (InvalidEntityDataException e) {
            violations.add(
                    new ConstraintViolation(user.getClass().getName(), "Username", user.getUserName(),
                            e.getMessage()));
        }
        try {
            isPasswordCorrect(user.getPassword());
        } catch (InvalidEntityDataException e) {
            violations.add(
                    new ConstraintViolation(user.getClass().getName(), "Password", user.getPassword(),
                            e.getMessage()));
        }
        try {
            isPhoneValid(user.getPhone());
        } catch (InvalidEntityDataException e) {
            violations.add(
                    new ConstraintViolation(user.getClass().getName(), "Phone", user.getPhone(),
                            e.getMessage()));
        }
        if (violations.size() > 0) {
            throw new ConstraintViolationException("Invalid User field", violations);
        }
        return user;
    }

    public boolean isValidEmailAddress(String email) throws InvalidEntityDataException {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            if (userRepo.findEmail(email)) {
                throw new InvalidEntityDataException("User with the same email already exists.");
            }
            // did not find email
        } else {
            throw new InvalidEntityDataException("Email is incorrect.");
        }
        return true;
    }

    public boolean isValidUsername(String username) throws InvalidEntityDataException {
        String regexPattern = "^[a-zA-Z0-9]{2,15}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(username);
        if (matcher.find()) {
            if (userRepo.findUsername(username)) {
                throw new InvalidEntityDataException("User with the same username already exists.");
            }
        } else {
            throw new InvalidEntityDataException("Username should include only word characters and numbers, and be between 2 and 15 characters.");
        }
        return true;
    }

    public boolean isPasswordCorrect(String password) throws InvalidEntityDataException {
        String regexPattern = "^(?=.{8,15}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9\\W]).*$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            throw new InvalidEntityDataException("Password must be 8 to 15 characters long, at least one digit, one capital letter, and one sign different than letter or digit.");
        }
        return true;
    }

    public boolean isPhoneValid(String phone) throws InvalidEntityDataException {
        String regexPattern = "((\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?)?\\d{3}[\\s.-]?\\d{4}";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.find()) {
            throw new InvalidEntityDataException("Phone number format is incorrect.");
        }
        return true;
    }

    //    public boolean validateFirstName(String firstName) throws InvalidEntityDataException {
//        if(firstName.length() < 2 || firstName.length() > 20) {
//            throw new InvalidEntityDataException("First name must be between 2 and 20 characters.");
//        }
//        return true;
//    }
    public boolean validateLastName(String lastName) throws InvalidEntityDataException {
        String regexPattern = "^[a-zA-Z]{2,20}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(lastName);
        if (matcher.find()) {
            return true;
        }
        throw new InvalidEntityDataException("Last name must be between 2 and 20 characters and contain only letters.");
    }

    // Method to validate first name with regex
    public boolean validateFirstName(String firstname) throws InvalidEntityDataException {
        String regexPattern = "^[a-zA-Z]{2,20}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(firstname);
        if (matcher.find()) {
            return true;
        }
        throw new InvalidEntityDataException("First name must be between 2 and 20 characters and contain only letters.");
    }


}
