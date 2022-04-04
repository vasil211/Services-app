package app.controller;

import app.model.User;
import app.service.UserService;
import app.view.RegistrationView;

public class RegistrationController {
    private final RegistrationView registrationView;
    private final UserService userService;

    public RegistrationController(RegistrationView registrationView, UserService userService) {
        this.registrationView = registrationView;
        this.userService = userService;
    }


    public void register() {
        User user;
        User user1 = null;
        do {
            user = new User();
            user = registrationView.register();
            user1 = userService.createUser(user);
            if (user1 != null) {
                System.out.println("Registration successful");
            }
        } while (user1 == null);
    }
}
