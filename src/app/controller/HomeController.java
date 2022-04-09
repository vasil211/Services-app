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
    private final MessagesController messagesController;
    private final ApplicationController applicationController;
    private final ServiceController serviceController;

    public HomeController(UserService userService, UserController userController,
                          AppointmentsService appointmentsService, PostService postService,
                          PostsController postsController, AppointmentController appointmentController,
                          RatingsController ratingsController, MessageService messageService,
                          MessagesController messagesController, ApplicationController applicationController,
                          ServiceController serviceController) {
        this.userService = userService;
        this.userController = userController;
        this.appointmentsService = appointmentsService;
        this.postService = postService;
        this.postsController = postsController;
        this.appointmentController = appointmentController;
        this.ratingsController = ratingsController;
        this.messageService = messageService;
        this.messagesController = messagesController;
        this.applicationController = applicationController;
        this.serviceController = serviceController;
    }

    public void home(User user) {
        var messages = new MessagesView(messageService);
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
                    new Menu.Option("Manage applications", () -> {
                        applicationController.moderatorApplicationsMenu();
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
                    new Menu.Option("Browse applications", () -> {
                        applicationController.moderatorApplicationsMenu();
                        return "";
                    }),
                    new Menu.Option("Search for service", () -> {
                        serviceController.controllerForModerator();
                        return "";
                    }),
                    new Menu.Option("Browse posts", () -> {
                        try {
                            postsController.moderatePostsMenu();
                        }catch (Exception e){
                            System.out.print("");
                        }
                        return "";
                    }),
                    new Menu.Option("Browse comments", () -> {
                            ratingsController.moderateCommentsMenu();
                        return "";
                    }),

                    new Menu.Option("Menage personal data", () -> {
                        userController.updatePersonalData(user);
                        return "";
                    })
            ));
            menu.show();
        } else if (user.getRole() == Role.SERVICE_PROVIDER) {
            var menu = new Menu("Provider Menu", List.of(
                    new Menu.Option("Appointments", () -> {
                        appointmentController.providerAppointmentsMenu(user);
                        return "";
                    }),
                    new Menu.Option("Messages", () -> {
                        messages.show(user);
                        return "";
                    }),
                    new Menu.Option("Menage Posts", () -> {
                        postsController.providerPostsMenu(user);
                        return "";  
                    }),
                    new Menu.Option("Create post", () -> {
                        postsController.createPost(user);
                        return "";
                    }),
                    new Menu.Option("Menage personal data", () -> {
                        userController.updatePersonalData(user);
                        return "";
                    }),
                    new Menu.Option("Back to user view", () -> {
                        user.setRole(Role.USER);
                        var menu2 = new Menu("User Menu", List.of(
                                new Menu.Option("Browse for service", () -> {
                                    serviceController.servicesMenu(user);
                                    return "";
                                }),
                                new Menu.Option("Messages", () -> {
                                    messages.show(user);
                                    return "";
                                }),
                                new Menu.Option("Appointments", () -> {
                                    appointmentController.userAppointments(user);
                                    return "";
                                })
                        ));
                        menu2.show();
                        user.setRole(Role.SERVICE_PROVIDER);
                        return "";
                    })
            ));
            menu.show();
        } else if (user.getRole() == Role.USER) {
            var menu = new Menu("User Menu", List.of(
                    new Menu.Option("Browse for service", () -> {
                        serviceController.servicesMenu(user);
                        return "";
                    }),
                    new Menu.Option("Messages", () -> {
                        messages.show(user);
                        return "";
                    }),
                    new Menu.Option("Appointments", () -> {
                        appointmentController.userAppointments(user);
                        return "";
                    }),
                    new Menu.Option("Menage personal data", () -> {
                        userController.updatePersonalData(user);

                        return "";
                    }),
                    new Menu.Option("Apply to become provider", () -> {
                        userController.applyToBecomeProvider(user);
                        return "";
                    })
            ));
            menu.show();
        }
    }
}
