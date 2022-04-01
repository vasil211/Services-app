package app.controller;

import app.model.Role;
import app.model.User;
import app.service.AppointmentsService;
import app.service.MessageService;
import app.service.PostService;
import app.service.UserService;
import app.view.Menu;
import app.view.MessagesView;

import java.util.List;

public class HomeController {
    private final UserService userService;
    private final UserController userController;
    private final AppointmentsService appointmentsService;
    private final PostService postService;
    private final MessageService messageService;
    private final PostsController postsController;
    private final AppointmentController appointmentController;
    private final RatingsController ratingsController;
    public HomeController(UserService userService, UserController userController, AppointmentsService appointmentsService, PostService postService, PostsController postsController, AppointmentController appointmentController, RatingsController ratingsController, MessageService messageService) {
        this.userService = userService;
        this.userController = userController;
        this.appointmentsService = appointmentsService;
        this.postService = postService;
        this.postsController = postsController;
        this.appointmentController = appointmentController;
        this.ratingsController = ratingsController;
        this.messageService = messageService;
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
            var menu = new Menu("Moderator Menu", List.of(
//                    new Menu.Option("Browse applications", () -> {
//                        // TODO: if?
//                        return "";
//                    }),
                    new Menu.Option("Browse posts", () -> {
                        try {
                            postsController.moderatePostsMenu();
                        }catch (Exception e){
                            System.out.print("");
                        }
                        return "";
                    }),
                    new Menu.Option("Browse comments", () -> {
                        try {
                            ratingsController.moderateCommentsMenu();
                        }catch (Exception e){
                            System.out.print("");
                        }
                        return "";
                    }),
                    new Menu.Option("Browse for service", () -> {
                        // TODO: use users but add DELETE   and add field moderated?
                        return "";
                    })
            ));
            menu.show();
        } else if (user.getRole() == Role.SERVICE_PROVIDER) {
            var messages = new MessagesView(messageService);
            var menu = new Menu("Provider Menu", List.of(
                    new Menu.Option("Appointments", () -> {
                        appointmentController.providerAppointmentsMenu(user);
                        return "";
                    }),
                    new Menu.Option("Messages", () -> {
                        messages.show(user);
                        // TODO: fill
                        return "";
                    }),
                    new Menu.Option("Menage Posts", () -> {
                        // TODO: fill
                        return "";  
                    }),
                    new Menu.Option("Add post", () -> {
                        // TODO: fill
                        return "";
                    }),
                    new Menu.Option("Back to user view", () -> {
                        // TODO: fill
                        return "";
                    })
                    // TODO: MORE
            ));
            menu.show();
        } else if (user.getRole() == Role.USER) {
            var menu = new Menu("User Menu", List.of(
                    new Menu.Option("Browse for service", () -> {
                        // TODO: fill
                        return "";
                    }),
                    new Menu.Option("Messages", () -> {
                        // TODO: fill
                        return "";
                    }),
                    new Menu.Option("Appointments", () -> {
                        // TODO: fill
                        return "";
                    }),
                    new Menu.Option("Menage personal data", () -> {
                        // TODO: fill    add   Apply to become provider
                        return "";
                    })
            ));
            menu.show();
        }
    }
}
