package app.controller;

import app.view.Menu;

import java.util.List;

public class MainController {
    private final LoginController loginController;
    private final RegistrationController registrationController;
    private final ServiceController serviceController;

    public MainController(LoginController loginController, RegistrationController registrationController,
                          ServiceController serviceController) {
        this.loginController = loginController;
        this.registrationController = registrationController;
        this.serviceController = serviceController;
    }

    public void main(){
        var menu = new Menu("Main", List.of(
                new Menu.Option("Look trough posts", () -> {
                    serviceController.lookThroughPosts();
                    return "";
                }),
                new Menu.Option("Login", () -> {
                    loginController.login();
                    return "";
                }),
                new Menu.Option("Register", () -> {
                    registrationController.register();
                    return "";
                })
        ));
        menu.show();
    }

}
