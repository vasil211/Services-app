package app.controller;

import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.User;
import app.service.ApplicationService;
import app.service.UserService;
import app.service.validators.UserValidation;
import app.view.ApplicationView;
import app.view.Menu;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class UserController {
    private final UserValidation userValidation;
    private final UserService userService;
    private final ApplicationView applicationView;
    private final ApplicationService applicationService;
    private final RegistrationController registrationController;


    public UserController(UserValidation userValidation, UserService userService , ApplicationView applicationView,
                          ApplicationService applicationService, RegistrationController registrationController) {
        this.userValidation = userValidation;
        this.userService = userService;
        this.applicationView = applicationView;
        this.applicationService = applicationService;
        this.registrationController = registrationController;
    }

    public User updatePersonalData(User user) {
        Scanner sc = new Scanner(System.in);
        var menu = new Menu("Update personal data menu", List.of(
                new Menu.Option("Chane Username", () -> {
                    String userName;
                    System.out.println("Enter username: ");
                    do {
                        userName = sc.nextLine();
                        try {
                            userValidation.validateUsername(userName);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid username: ");
                        }
                    } while (true);
                    userService.updateUser(user, "username", userName);
                    return "Username changed successfully.";
                }),
                new Menu.Option("Chane password", () -> {
                    String password;
                    System.out.println("Enter password: ");
                    do {
                        password = sc.nextLine();
                        try {
                            userValidation.validatePassword(password);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid password: ");
                        }
                    } while (true);
                    userService.updateUser(user, "password", password);
                    return "Password changed successfully.";
                }),
                new Menu.Option("Change email", () -> {
                    String email;
                    System.out.println("Enter email: ");
                    do {
                        email = sc.nextLine();
                        try {
                            userValidation.validateEmailAddress(email);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid email:");
                        }
                    } while (true);
                    userService.updateUser(user, "email", email);
                    return "Email changed successfully.";
                }),
                new Menu.Option("Chane First name", () -> {
                    String firstName;
                    System.out.println("Enter First name: ");
                    do {
                        firstName = sc.nextLine();
                        try {
                            userValidation.validateFirstName(firstName);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid First name: ");
                        }
                    } while (true);
                    userService.updateUser(user, "firstName", firstName);
                    return "First name changed successfully.";
                }),
                new Menu.Option("Change Last Name", () -> {
                    String lastName;
                    System.out.println("Enter Last name: ");
                    do {
                        lastName = sc.nextLine();
                        try {
                            userValidation.validateLastName(lastName);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid Last name: ");
                        }
                    } while (true);
                    userService.updateUser(user, "lastName", lastName);
                    return "Last name changed successfully.";
                }),
                new Menu.Option("Chane Phone", () -> {
                    String phone;
                    System.out.println("Enter Phone:");
                    do {
                        phone = sc.nextLine();
                        try {
                            userValidation.validatePhone(phone);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid phone:");
                        }
                    } while (true);
                    userService.updateUser(user, "phone", phone);
                    return "Phone changed successfully.";
                })
        ));
        menu.show();
        return user;
    }


    public User updateUser() {
        Scanner sc = new Scanner(System.in);
        AtomicReference<User> user = new AtomicReference<>();
        String idStr;
        long id = 0L;
        do {
            System.out.println("Enter user id to manage: ");
            idStr = sc.nextLine();
            try {
                id = Long.parseLong(idStr);
                user.set(userService.getUserById(id));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter valid id: ");
            } catch (NonexistingEntityException e) {
                System.out.println(e.getMessage());
                System.out.println("Enter valid user id: ");
            }
        } while (true);
        System.out.println(user.toString());
        var menu = new Menu("Update user with id: '" + user.get().getId() + "' personal data", List.of(
                new Menu.Option("Chane Username", () -> {
                    String userName;
                    do {
                        System.out.println("Enter username: ");
                        userName = sc.nextLine();
                        try {
                            userValidation.validateUsername(userName);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid username: ");
                        }
                    } while (true);
                    userService.updateUser(user.get(), "username", userName);
                    return "Username changed successfully.";
                }),
                new Menu.Option("Chane password", () -> {
                    String password;
                    do {
                        System.out.println("Enter password: ");
                        password = sc.nextLine();
                        try {
                            userValidation.validatePassword(password);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid password: ");
                        }
                    } while (true);
                    userService.updateUser(user.get(), "password", password);
                    return "Password changed successfully.";
                }),
                new Menu.Option("Change email", () -> {
                    String email;
                    do {
                        System.out.println("Enter email: ");
                        email = sc.nextLine();
                        try {
                            userValidation.validateEmailAddress(email);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid email:");
                        }
                    } while (true);
                    userService.updateUser(user.get(), "email", email);
                    return "Email changed successfully.";
                }),
                new Menu.Option("Chane First name", () -> {
                    String firstName;
                    do {
                        System.out.println("Enter First name: ");
                        firstName = sc.nextLine();
                        try {
                            userValidation.validateFirstName(firstName);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid First name: ");
                        }
                    } while (true);
                    userService.updateUser(user.get(), "firstName", firstName);
                    return "First name changed successfully.";
                }),
                new Menu.Option("Change Last Name", () -> {
                    String lastName;
                    do {
                        System.out.println("Enter Last name: ");
                        lastName = sc.nextLine();
                        try {
                            userValidation.validateLastName(lastName);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid Last name: ");
                        }
                    } while (true);
                    userService.updateUser(user.get(), "lastName", lastName);
                    return "Last name changed successfully.";
                }),
                new Menu.Option("Chane Phone", () -> {
                    String phone;
                    do {
                        System.out.println("Enter Phone:");
                        phone = sc.nextLine();
                        try {
                            userValidation.validatePhone(phone);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Enter valid phone:");
                        }
                    } while (true);
                    userService.updateUser(user.get(), "phone", phone);
                    return "Phone changed successfully.";
                })
        ));
        menu.show();
        System.out.println(user.get());
        return user.get();
    }

    public void userDataMenu(User user) {
        var menu = new Menu("Admin Menu", List.of(
                new Menu.Option("Manage personal data", () -> {
                    updatePersonalData(user);
                    return "Personal data update successful.";
                }),

                new Menu.Option("List all Admins and data", () -> {
                    var books = userService.getAllAdmins();
                    books.forEach(System.out::println);
                    return "Admins loaded successfully.";
                }),
                new Menu.Option("Show all Moderators and data", () -> {
                    var books = userService.getAllModerators();
                    books.forEach(System.out::println);
                    return "Moderators loaded successfully.";
                }),
                new Menu.Option("Show all Service Providers and data", () -> {
                    var books = userService.getAllServiceProviders();
                    books.forEach(System.out::println);
                    return "Users loaded successfully.";
                }),
                new Menu.Option("List all Users and data", () -> {
                    var books = userService.getAllUsers();
                    books.forEach(System.out::println);
                    return "Users loaded successfully.";
                }),
                new Menu.Option("Manage user", () -> {
                    updateUser();
                    return "Users loaded successfully.";
                }),
                new Menu.Option("Register User", () -> {
                    registrationController.register();
                    return "User registered.";
                })
        ));
        menu.show();
    }

    public void applyToBecomeProvider(User user) {
        var applied = applicationService.getLastApplicationForUser(user);
        Scanner sc = new Scanner(System.in);
        if (applied.getId() == null) {
            System.out.println("Do you want to become a service provider? (yes/no)");
            var answer = sc.nextLine();
            if (answer.equals("yes")) {
                var application = applicationView.applyToBecomeProvider(user);
                applicationService.createApplication(application);
                System.out.println("Application submitted successfully.");
            }
        } else {
            System.out.println("\nYou already applied to become a service provider.");
            System.out.println("Application status: " + applied.getStatus());
            if(applied.getStatus().equals("REJECTED")) {
                System.out.println("Reason: ");
                System.out.println(applied.getReasonForRejection());
                System.out.println(applied.getRejected().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                System.out.println("\nDo you want to apply again? (yes/no)");
                var answer = sc.nextLine();
                if (answer.equals("yes")) {
                    var application = applicationView.applyToBecomeProvider(user);
                    applicationService.createApplication(application);
                    System.out.println("Application submitted successfully.");
                }
            }
        }
    }
}
