package app.view;

import app.exeption.InvalidEntityDataException;
import app.model.Role;
import app.model.User;
import app.service.validators.UserValidation;

import java.time.LocalDateTime;
import java.util.Scanner;

public class RegistrationView {
    private final UserValidation userValidation;

    public RegistrationView(UserValidation userValidation) {
        this.userValidation = userValidation;
    }

    public User register() {
        Scanner sc = new Scanner(System.in);
        String firstName;
        String lastName;
        String username;
        String email;
        String password;
        String confirmPassword;
        String phoneNumber;
        User user = new User();
        System.out.println("Hello, we are happy to see you here!");
        do {
            System.out.println("Please, enter First name:");
            firstName = sc.nextLine();
            try {
                userValidation.validateFirstName(firstName);
                break;
            } catch (InvalidEntityDataException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        user.setFirstName(firstName);
        do {
            System.out.println("Please, enter Last name:");
            lastName = sc.nextLine();
            try {
                userValidation.validateLastName(lastName);
                break;
            } catch (InvalidEntityDataException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        user.setLastName(lastName);
        do {
            System.out.println("Please, enter Username:");
            username = sc.nextLine();
            try {
                userValidation.validateUsername(username);
                break;
            } catch (InvalidEntityDataException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        user.setUserName(username);
        do {
            System.out.println("Please, enter Email:");
            email = sc.nextLine();
            try {
                userValidation.validateEmailAddress(email);
                break;
            } catch (InvalidEntityDataException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        user.setEmail(email);
        do {
            System.out.println("Please, enter password:");
            password = sc.nextLine();
            try {
                userValidation.validatePassword(password);
                System.out.println("Conform password:");
                confirmPassword = sc.nextLine();
                if (!password.equals(confirmPassword)) {
                    System.out.println("Passwords do not match! Try again!");
                } else {
                    break;
                }
            } catch (InvalidEntityDataException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        user.setPassword(password);
        do {
            System.out.println("Please, enter Phone:");
            phoneNumber = sc.nextLine();
            try {
                userValidation.validatePhone(phoneNumber);
                break;
            } catch (InvalidEntityDataException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        user.setPhone(phoneNumber);
        user.setRole(Role.USER);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastOnline(LocalDateTime.now());
        return user;
    }
}
