package app.controller;


import app.model.User;
import app.service.UserService;
import app.view.LoginView;

public class LoginController {
    private UserService userService;
    private LoginView loginView;
    private HomeController homeController;
    public LoginController(UserService userService, LoginView loginView,HomeController homeController) {
        this.userService = userService;
        this.loginView = loginView;
        this.homeController = homeController;
    }

    public void login() {
        User user;
        User user1 = null;
        do {
            user = loginView.login();
            String userName = user.getUserName();
            String password = user.getPassword();
            user1 = userService.login(userName, password);
        } while (user1 == null);
        homeController.home(user1);
    }
}
