package app.controller;

import app.model.Rating;
import app.model.Role;
import app.model.User;
import app.service.AppointmentsService;
import app.service.PostService;
import app.service.UserService;
import app.view.Menu;

import java.util.List;

public class HomeController {
    private final UserService userService;
    private final UserController userController;
    private final AppointmentsService appointmentsService;
    private final PostService postService;
    private final PostsController postsController;
    private final AppointmentController appointmentController;
    private final RatingsController ratingsController;
    public HomeController(UserService userService, UserController userController, AppointmentsService appointmentsService, PostService postService, PostsController postsController, AppointmentController appointmentController, RatingsController ratingsController) {
        this.userService = userService;
        this.userController = userController;
        this.appointmentsService = appointmentsService;
        this.postService = postService;
        this.postsController = postsController;
        this.appointmentController = appointmentController;
        this.ratingsController = ratingsController;
    }

    public void home(User user) {

        if (user.getRole() == Role.ADMIN) {
            var menu = new Menu("Admin Menu", List.of(
                    new Menu.Option("Manage Users Data", () -> {
                        userController.userDataMenu(user);
                        return "";
                    }),
                    new Menu.Option("Manage categoryes and posts", () -> {
                        postsController.adminPostsMenu();
                        return "";
                    }),
                    new Menu.Option("Manage appointments", () -> {
                        appointmentController.adminAppointmentsMenu();
                        return "";
                    }),
                    new Menu.Option("Manage ratings", () -> {
                        ratingsController.adminRatingsMenu();
                        return "";
                    })

            ));
            menu.show();
        } else if (user.getRole() == Role.MODERATOR) {

        } else if (user.getRole() == Role.SERVICE_PROVIDER) {

        } else if (user.getRole() == Role.USER) {

        }
    }
}
