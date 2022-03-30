package app.controller;

import app.exeption.NonexistingEntityException;
import app.model.Appointments;
import app.model.User;
import app.service.AppointmentsService;
import app.service.UserService;
import app.view.Menu;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class AppointmentController {
    private final AppointmentsService appointmentsService;
    private final UserService userService;

    public AppointmentController(AppointmentsService appointmentsService, UserService userService) {
        this.appointmentsService = appointmentsService;
        this.userService = userService;
    }

    public void adminAppointmentsMenu() {
        var menu = new Menu("Appointments Menu", List.of(
                new Menu.Option("List all PENDING", () -> {
                    var appointments = appointmentsService.findAllPending() ;
                    appointments.forEach(appointment ->{
                        StringJoiner joiner = new StringJoiner("", "\n", " ");
                        joiner.add("Appointment ID: " + appointment.getId());
                        joiner.add("Provider: " + appointment.getServiceProvider().getFirstName() + " "
                                + appointment.getServiceProvider().getLastName());
                            });
                    return "";
                }),
                new Menu.Option("List all ACCEPTED", () -> {
                    var appointments = appointmentsService.findAllAccepted() ;
                    appointments.forEach(appointment ->{
                        // TODO: add appointment details
                    });
                    return "";
                }),
                new Menu.Option("List all FINISHED", () -> {
                        // TODO: add appointment details
                    return "";
                }),
                new Menu.Option("List all DECLINED", () -> {
                    // TODO: add appointment details
                    return "";
                }),
                new Menu.Option("List all for User", () -> {
                    Scanner sc = new Scanner(System.in);
                    String idStr = null;
                    long id = 0;
                    User user = null;
                    do {
                        System.out.println("Enter User ID: ");
                        idStr = sc.nextLine();
                        id = Long.parseLong(idStr);
                        try {
                            user = userService.getUserById(id);
                            break;
                        } catch (NonexistingEntityException e) {
                            e.printStackTrace();
                        }
                    }while (true);
                    appointmentsForUser(user);
                    return "";
                })

        ));
        menu.show();
    }

    public void appointmentsForUser(User user){
        System.out.println("Appointments for: " + user.getFirstName() + " " + user.getLastName());
        var menu = new Menu("Admin Menu", List.of(
                new Menu.Option("List all PENDING", () -> {
                    Collection<Appointments> appointments = null;
                    try {
                        appointments = appointmentsService.findAllPendingForUser(user.getId());
                        appointments.forEach(appointment ->{
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Appointment ID: " + appointment.getId());
                            joiner.add("Provider: " + appointment.getServiceProvider().getFirstName() + " "
                                    + appointment.getServiceProvider().getLastName());
                            // TODO: add appointment details
                        });
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "";
                }),
                new Menu.Option("List all ACCEPTED", () -> {
                    Collection<Appointments> appointments = null;
                    try {
                        appointments = appointmentsService.findAllAcceptedForUser(user.getId());
                        appointments.forEach(appointment ->{
                            // TODO: add appointment details
                        });
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }

                    return "";
                }),
                new Menu.Option("List all FINISHED", () -> {
                    Collection<Appointments> appointments = null;
                    try {
                        appointments = appointmentsService.findAllFinishedForUser(user.getId());
                        appointments.forEach(appointment ->{
                            // TODO: add appointment details
                        });
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }

                    return "";
                }),
                new Menu.Option("List all DECLINED", () -> {
                    Collection<Appointments> appointments = null;
                    try {
                        appointments = appointmentsService.findAllDeclinedForUser(user.getId());
                        appointments.forEach(appointment ->{
                            // TODO: add appointment details
                        });
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }

                    return "";
                })

        ));
        menu.show();
    }
}
