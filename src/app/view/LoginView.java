package app.view;

import app.exeption.InvalidEntityDataException;
import app.model.User;
import app.service.validators.UserValidation;

import java.util.Scanner;

public class LoginView {

    private UserValidation userValidation;
    public LoginView(UserValidation userValidation){
        this.userValidation = userValidation;
    }

    public User login(){
        Scanner scanner = new Scanner(System.in);
        String password;
        String username;;

        System.out.println("Enter username: ");
        username = scanner.nextLine();
        do {
            System.out.println("Enter password:");
            password = scanner.nextLine();
            try {
                userValidation.validatePassword(password);
                break;
            } catch (InvalidEntityDataException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }while (true);
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        return user;
    }
}
