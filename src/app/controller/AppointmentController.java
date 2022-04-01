package app.controller;

import app.exeption.NonexistingEntityException;
import app.model.Appointments;
import app.model.User;
import app.service.AppointmentsService;
import app.service.UserService;
import app.view.Menu;

import java.time.format.DateTimeFormatter;
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
                    var appointments = appointmentsService.findAllPending();
                    appointments.forEach(appointment -> {
                        StringJoiner joiner = new StringJoiner("", "\n", " ");
                        joiner.add("Appointment ID: " + appointment.getId());
                        joiner.add("\nProvider ID: " + appointment.getServiceProvider().getId()
                                + " Name:" + appointment.getServiceProvider().getFirstName() + " "
                                + appointment.getServiceProvider().getLastName());
                        joiner.add("\nClient ID: " + appointment.getUser().getId()
                                + " Name:" + appointment.getUser().getFirstName() + " "
                                + appointment.getUser().getLastName());
                        joiner.add("\nDate: " + appointment.getCreated()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        joiner.add("Post:  ID: " + appointment.getPost().getId() + " "
                                + appointment.getPost().getName());
                    });
                    return "";
                }),
                new Menu.Option("List all ACCEPTED", () -> {
                    var appointments = appointmentsService.findAllAccepted();
                    appointments.forEach(appointment -> {
                        StringJoiner joiner = new StringJoiner("", "\n", " ");
                        joiner.add("Appointment ID: " + appointment.getId());
                        joiner.add("\nProvider ID: " + appointment.getServiceProvider().getId()
                                + " Name:" + appointment.getServiceProvider().getFirstName() + " "
                                + appointment.getServiceProvider().getLastName());
                        joiner.add("\nClient ID: " + appointment.getUser().getId()
                                + " Name:" + appointment.getUser().getFirstName() + " "
                                + appointment.getUser().getLastName());
                        joiner.add("\nDate: " + appointment.getCreated()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        joiner.add("Post:  ID: " + appointment.getPost().getId() + " "
                                + appointment.getPost().getName());
                    });
                    return "";
                }),
                new Menu.Option("List all FINISHED", () -> {
                    var appointments = appointmentsService.findAllFinished();
                    appointments.forEach(appointment -> {
                        StringJoiner joiner = new StringJoiner("", "\n", " ");
                        joiner.add("Appointment ID: " + appointment.getId());
                        joiner.add("\nProvider ID: " + appointment.getServiceProvider().getId()
                                + " Name:" + appointment.getServiceProvider().getFirstName() + " "
                                + appointment.getServiceProvider().getLastName());
                        joiner.add("\nClient ID: " + appointment.getUser().getId()
                                + " Name:" + appointment.getUser().getFirstName() + " "
                                + appointment.getUser().getLastName());
                        joiner.add("\nDate: " + appointment.getCreated()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        joiner.add("Post:  ID: " + appointment.getPost().getId() + " "
                                + appointment.getPost().getName());
                    });
                    return "";
                }),
                new Menu.Option("List all DECLINED", () -> {
                    var appointments = appointmentsService.findAllDeclined();
                    appointments.forEach(appointment -> {
                        StringJoiner joiner = new StringJoiner("", "\n", " ");
                        joiner.add("Appointment ID: " + appointment.getId());
                        joiner.add("\nProvider ID: " + appointment.getServiceProvider().getId()
                                + " Name:" + appointment.getServiceProvider().getFirstName() + " "
                                + appointment.getServiceProvider().getLastName());
                        joiner.add("\nClient ID: " + appointment.getUser().getId()
                                + " Name:" + appointment.getUser().getFirstName() + " "
                                + appointment.getUser().getLastName());
                        joiner.add("\nDate: " + appointment.getCreated()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        joiner.add("Post:  ID: " + appointment.getPost().getId() + " "
                                + appointment.getPost().getName());
                    });
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
                    } while (true);
                    appointmentsForUser(user);
                    return "";
                })
        ));
        menu.show();
    }

    public void appointmentsForUser(User user) {
        System.out.println("Appointments for: " + user.getFirstName() + " " + user.getLastName());
        var menu = new Menu("Admin Menu", List.of(
                new Menu.Option("List all PENDING", () -> {
                    Collection<Appointments> appointments;
                    try {
                        appointments = appointmentsService.findAllPendingForUser(user.getId());
                        appointments.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Appointment ID: " + appointment.getId());
                            joiner.add("State: " + appointment.getState());
                            joiner.add("\nClient ID: " + appointment.getUser().getId() + " Name:"
                                    + appointment.getUser().getFirstName() + " "
                                    + appointment.getUser().getLastName());
                            joiner.add("Date: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            joiner.add("Post:  ID: " + appointment.getPost().getId() + " "
                                    + appointment.getPost().getName());
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
                        appointments.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Appointment ID: " + appointment.getId());
                            joiner.add("State: " + appointment.getState());
                            joiner.add("\nClient ID: " + appointment.getUser().getId() + " Name:"
                                    + appointment.getUser().getFirstName() + " "
                                    + appointment.getUser().getLastName());
                            joiner.add("Date: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            joiner.add("Post:  ID: " + appointment.getPost().getId() + " "
                                    + appointment.getPost().getName());
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
                        appointments.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Appointment ID: " + appointment.getId());
                            joiner.add("State: " + appointment.getState());
                            joiner.add("\nClient ID: " + appointment.getUser().getId() + " Name:"
                                    + appointment.getUser().getFirstName() + " "
                                    + appointment.getUser().getLastName());
                            joiner.add("Date: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            joiner.add("Post:  ID: " + appointment.getPost().getId() + " "
                                    + appointment.getPost().getName());
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
                        appointments.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Appointment ID: " + appointment.getId());
                            joiner.add("State: " + appointment.getState());
                            joiner.add("\nClient ID: " + appointment.getUser().getId() + " Name:"
                                    + appointment.getUser().getFirstName() + " "
                                    + appointment.getUser().getLastName());
                            joiner.add("Date: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            joiner.add("Post:  ID: " + appointment.getPost().getId() + " "
                                    + appointment.getPost().getName());
                        });
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "";
                })
        ));
        menu.show();
    }

    public void providerAppointmentsMenu(User user) {
        Scanner sc = new Scanner(System.in);
        Collection<Appointments> pending = null;
        Collection<Appointments> accepted = null;
        Collection<Appointments> finished = null;
        try {
            pending = appointmentsService.findAllPendingForUser(user.getId());

        } catch (NonexistingEntityException e) {
            System.out.print("");
        }
        try {
            accepted = appointmentsService.findAllAcceptedForUser(user.getId());
        } catch (NonexistingEntityException e) {
            System.out.print("");
        }
        try {
            finished = appointmentsService.findAllFinishedForUser(user.getId());
        } catch (NonexistingEntityException e) {
            System.out.print("");
        }
        int pendingSize = 0;
        int acceptedSize = 0;
        int finishedSize = 0;
        if (pending != null) {
            pendingSize = pending.size();
        }
        if (accepted != null) {
            acceptedSize = accepted.size();
        }
        if (finished != null) {
            finishedSize = finished.size();
        }
        Collection<Appointments> finalPending = pending;
        Collection<Appointments> finalAccepted = accepted;
        Collection<Appointments> finalFinished = finished;
        var menu = new Menu("Appointments", List.of(
                new Menu.Option("See all pending - you have: " + pendingSize, () -> {
                    if (finalPending != null) {
                        finalPending.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add(appointment.getUser().getFirstName() + " "
                                    + appointment.getUser().getLastName());
                            joiner.add(" has made an appointment for: " + appointment.getPost().getName());
                            joiner.add("\nPhone: " + appointment.getUser().getPhone());
                            joiner.add("\nDate: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            System.out.print("\n");
                            System.out.println(joiner.toString());
                            var options = new Menu("Options:", List.of(
                                    new Menu.Option("Accept", () -> {
                                        appointmentsService.acceptAppointment(appointment.getId());
                                        return "Appointment accepted";
                                    }),
                                    new Menu.Option("Decline", () -> {
                                        System.out.println("Give a reason for declining the appointment: ");
                                        String reason = sc.nextLine();
                                        appointmentsService.declineAppointment(appointment.getId(), reason);
                                        return "Appointment declined";
                                    }),
                                    new Menu.Option("Go to next", () -> {
                                        return "Skipped";
                                    })
                            ));
                            var check = options.showForForEach();
                            if (check) throw new RuntimeException();

                        });
                    } else {
                        System.out.println("You have no pending appointments");
                    }
                    return "";
                }),
                new Menu.Option("See all accepted - you have: " + acceptedSize, () -> {
                    if (finalAccepted != null) {
                        finalAccepted.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add(appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName());
                            joiner.add(" has made an appointment for: " + appointment.getPost().getName());
                            joiner.add("\nPhone: " + appointment.getUser().getPhone());
                            joiner.add("\nDate: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            joiner.add("\nAccepted on: " + appointment.getUpdated());
                            System.out.print("\n");
                            System.out.println(joiner.toString());
                            var options = new Menu("Options:", List.of(
                                    new Menu.Option("Mark as finished", () -> {
                                        appointmentsService.finishAppointment(appointment.getId());
                                        return "Appointment finished";
                                    }),
                                    new Menu.Option("Decline", () -> {
                                        System.out.println("Give a reason for declining the appointment: ");
                                        String reason = sc.nextLine();
                                        appointmentsService.declineAppointment(appointment.getId(), reason);
                                        return "Appointment declined";
                                    }),
                                    new Menu.Option("Go to next", () -> {
                                        return "Skipped";
                                    })
                            ));
                            var check = options.showForForEach();
                            if (check) throw new RuntimeException();
                        });
                    } else {
                        System.out.println("You have no accepted appointments");
                    }
                    return "";
                }),
                new Menu.Option("See all finished - you have: " + finishedSize, () -> {
                    if (finalFinished != null) {
                        finalFinished.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add(appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName());
                            joiner.add(" made an appointment for: " + appointment.getPost().getName());
                            joiner.add("\nPhone: " + appointment.getUser().getPhone());
                            joiner.add("\nDate: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            joiner.add("\nFinished on: " + appointment.getUpdated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            System.out.println(joiner.toString());
                        });
                    } else {
                        System.out.println("You have no finished appointments");
                    }
                    return "";
                })
        ));
        menu.show();
    }
}
