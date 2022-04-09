package app.controller;

import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Appointments;
import app.model.Post;
import app.model.User;
import app.service.AppointmentsService;
import app.service.UserService;
import app.view.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AppointmentController {
    private final AppointmentsService appointmentsService;
    private final UserService userService;
    private final RatingsController ratingsController;

    public AppointmentController(AppointmentsService appointmentsService, UserService userService,
                                 RatingsController ratingsController) {
        this.appointmentsService = appointmentsService;
        this.userService = userService;
        this.ratingsController = ratingsController;
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
                        appointments = appointmentsService.findAllPendingForProvider(user.getId());
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
                        appointments = appointmentsService.findAllAcceptedForProvider(user.getId());
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
                        appointments = appointmentsService.findAllFinishedForProvider(user.getId());
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
                        appointments = appointmentsService.findAllDeclinedForProvider(user.getId());
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
            pending = appointmentsService.findAllPendingForProvider(user.getId());

        } catch (NonexistingEntityException e) {
            System.out.print("");
        }
        try {
            accepted = appointmentsService.findAllAcceptedForProvider(user.getId());
        } catch (NonexistingEntityException e) {
            System.out.print("");
        }
        try {
            finished = appointmentsService.findAllFinishedForProvider(user.getId());
        } catch (NonexistingEntityException e) {
            System.out.print("");
        }
        if (pending == null) {
            pending = new ArrayList<>();
        }
        if (accepted == null) {
            accepted = new ArrayList<>();
        }
        if (finished == null) {
            finished = new ArrayList<>();
        }
        Collection<Appointments> finalPending = pending;
        Collection<Appointments> finalAccepted = accepted;
        Collection<Appointments> finalFinished = finished;

        var menu = new Menu("Appointments", List.of(
                new Menu.Option("See all pending", () -> {
                    if (finalPending.size() > 0) {
                        var iter = finalPending.iterator();
                        while (iter.hasNext()) {
                            var appointment = iter.next();
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add(appointment.getUser().getFirstName() + " "
                                    + appointment.getUser().getLastName());
                            joiner.add(" has made an appointment for: " + appointment.getPost().getName());
                            joiner.add("\nPhone: " + appointment.getUser().getPhone());
                            joiner.add("\nAddress: " + appointment.getAddress());
                            joiner.add("\nDate: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            System.out.print("\n");
                            System.out.println(joiner.toString());
                            var options = new Menu("Options:", List.of(
                                    new Menu.Option("Accept", () -> {
                                        appointmentsService.acceptAppointment(appointment.getId());
                                        finalAccepted.add(appointment);
                                        iter.remove();
                                        System.out.println("Appointment accepted");
                                        return "Appointment accepted";
                                    }),
                                    new Menu.Option("Decline", () -> {
                                        System.out.println("Give a reason for declining the appointment: ");
                                        String reason = sc.nextLine();
                                        appointmentsService.declineAppointment(appointment.getId(), reason);
                                        iter.remove();
                                        System.out.println("Appointment declined");
                                        return "Appointment declined";
                                    }),
                                    new Menu.Option("Go to next", () -> {
                                        return "Skipped";
                                    })
                            ));
                            var check = options.showForForEach();
                            if (check) break;

                        }
                    } else {
                        System.out.println("You have no pending appointments");
                    }
                    return "";
                }),
                new Menu.Option("See all accepted", () -> {
                    if (finalAccepted.size() > 0) {
                        var iter = finalAccepted.iterator();
                        while (iter.hasNext()) {
                            var appointment = iter.next();
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add(appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName());
                            joiner.add(" has made an appointment for: " + appointment.getPost().getName());
                            joiner.add("\nPhone: " + appointment.getUser().getPhone());
                            joiner.add("\nAddress: " + appointment.getAddress());
                            joiner.add("\nDate: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            joiner.add("\nAccepted on: " + appointment.getUpdated());
                            System.out.print("\n");
                            System.out.println(joiner.toString());
                            var options = new Menu("Options:", List.of(
                                    new Menu.Option("Mark as finished", () -> {
                                        appointmentsService.finishAppointment(appointment.getId());
                                        finalFinished.add(appointment);
                                        iter.remove();
                                        System.out.println("Appointment finished");
                                        return "Appointment finished";
                                    }),
                                    new Menu.Option("Decline", () -> {
                                        System.out.println("Give a reason for declining the appointment: ");
                                        String reason = sc.nextLine();
                                        appointmentsService.declineAppointment(appointment.getId(), reason);
                                        iter.remove();
                                        System.out.println("Appointment declined");
                                        return "Appointment declined";
                                    }),
                                    new Menu.Option("Go to next", () -> {
                                        return "Skipped";
                                    })
                            ));
                            var check = options.showForForEach();
                            if (check) break;
                        }
                    } else {
                        System.out.println("You have no accepted appointments");
                    }
                    return "";
                }),
                new Menu.Option("See all finished", () -> {
                    if (finalFinished.size() > 0) {
                        for (var appointment : finalFinished) {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add(appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName());
                            joiner.add(" made an appointment for: " + appointment.getPost().getName());
                            joiner.add("\nPhone: " + appointment.getUser().getPhone());
                            joiner.add("\nAddress: " + appointment.getAddress());
                            joiner.add("\nDate: " + appointment.getCreated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            joiner.add("\nFinished on: " + appointment.getUpdated()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            System.out.println(joiner.toString());
                        }
                    } else {
                        System.out.println("You have no finished appointments");
                    }
                    return "";
                })
        ));
        menu.show();
    }

    public void userAppointments(User user) {
        Collection<Appointments> pending = appointmentsService.findAllPendingFromUser(user.getId());
        Collection<Appointments> accepted = appointmentsService.findAllAcceptedFromUser(user.getId());
        Collection<Appointments> finished = appointmentsService.findAllFinishedFromUser(user.getId());
        Collection<Appointments> declined = appointmentsService.findAllDeclinedFromUser(user.getId());
        int pendingSize = 0;
        int acceptedSize = 0;
        int finishedSize = 0;
        int declinedSize = 0;
        if (!pending.isEmpty()) {
            pendingSize = pending.size();
        }
        if (!accepted.isEmpty()) {
            acceptedSize = accepted.size();
        }
        if (!finished.isEmpty()) {
            finishedSize = finished.size();
        }
        if (!declined.isEmpty()) {
            declinedSize = declined.size();
        }
        var menu = new Menu("", List.of(
                new Menu.Option("List pending, you have " + pendingSize, () -> {
                    if (!pending.isEmpty()) {
                        AtomicInteger count = new AtomicInteger(1);
                        pending.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Number: " + count.getAndIncrement());
                            joiner.add("\nFor: " + appointment.getPost().getName());
                            joiner.add("\nFrom: " + appointment.getServiceProvider().getFirstName() + " "
                                    + appointment.getServiceProvider().getLastName());
                            joiner.add("\nAddress: " + appointment.getAddress());
                            joiner.add("\nCreated: " + appointment.getCreated());
                            System.out.println(joiner);
                        });
                        ArrayList<Appointments> pendingAppointments = new ArrayList<Appointments>(pending);
                        ratingsController.openPostFromAppointment(pendingAppointments);
                    } else {
                        System.out.println("You have no pending appointments");
                    }
                    return "";
                }),
                new Menu.Option("List all accepted, you have " + acceptedSize, () -> {
                    if (!accepted.isEmpty()) {
                        AtomicInteger count = new AtomicInteger(1);
                        accepted.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Number: " + count.getAndIncrement());
                            joiner.add("\nFor: " + appointment.getPost().getName());
                            joiner.add("\nFrom: " + appointment.getServiceProvider().getFirstName() + " "
                                    + appointment.getServiceProvider().getLastName());
                            joiner.add("\nAddress: " + appointment.getAddress());
                            joiner.add("\nCreated: " + appointment.getCreated());
                            joiner.add("\nAccepted: " + appointment.getUpdated());
                            System.out.println(joiner);
                        });
                        ArrayList<Appointments> acceptedAppointments = new ArrayList<Appointments>(accepted);
                        ratingsController.openPostFromAppointment(acceptedAppointments);
                    } else {
                        System.out.println("You have no accepted appointments");
                    }
                    return "";
                }),
                new Menu.Option("List all finished, you have " + finishedSize, () -> {
                    if (!finished.isEmpty()) {
                        AtomicInteger count = new AtomicInteger(1);
                        finished.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Number: " + count.getAndIncrement());
                            joiner.add("\nFor: " + appointment.getPost().getName());
                            joiner.add("\nFrom: " + appointment.getServiceProvider().getFirstName() + " "
                                    + appointment.getServiceProvider().getLastName());
                            // joiner.add("\nAddress: " + appointment.getAddress());
                            joiner.add("\nCreated: " + appointment.getCreated());
                            joiner.add("\nFinished: " + appointment.getUpdated());
                            System.out.println(joiner);
                        });
                        ArrayList<Appointments> finishedAppointments = new ArrayList<Appointments>(finished);
                        ratingsController.openPostFromFinishedAppointment(finishedAppointments, user);
                    } else {
                        System.out.println("You have no finished appointments");
                    }
                    return "";
                }),
                new Menu.Option("List all declined, you have " + declinedSize, () -> {
                    if (!declined.isEmpty()) {
                        AtomicInteger count = new AtomicInteger(1);
                        declined.forEach(appointment -> {
                            StringJoiner joiner = new StringJoiner("", "\n", " ");
                            joiner.add("Number: " + count.getAndIncrement());
                            joiner.add("\nFor: " + appointment.getPost().getName());
                            joiner.add("\nFrom: " + appointment.getServiceProvider().getFirstName() + " "
                                    + appointment.getServiceProvider().getLastName());
                            joiner.add("\nAddress: " + appointment.getAddress());
                            joiner.add("\nCreated: " + appointment.getCreated());
                            joiner.add("\nDeclined: " + appointment.getUpdated());
                            joiner.add("\nReason: " + appointment.getDeclineComment());
                            System.out.println(joiner);
                        });
                        ArrayList<Appointments> declinedAppointments = new ArrayList<Appointments>(declined);
                        ratingsController.openPostFromAppointment(declinedAppointments);
                    } else {
                        System.out.println("You have no declined appointments");
                    }
                    return "";
                })
        ));
        menu.show();
    }

    public void createAppointment(User user, Post post) {
        Scanner sc = new Scanner(System.in);
        var past = appointmentsService.findAllPendingFromUser(user.getId());
        if (past.isEmpty()) {
            System.out.println("\nAre you sure you want to create an appointment for " + post.getName() + "? <yes/no>");
            String answer = sc.nextLine();
            if (answer.equals("yes")) {
                String address = "";
                do {
                    System.out.println("\nPlease enter the address of the appointment");
                    address = sc.nextLine();
                    if (address.length() > 200 || address.length() < 2) {
                        System.out.println("\nAddress must be between 2 and 200 characters");
                    }
                } while (address.length() > 200 || address.length() < 2);
                Appointments appointment = new Appointments();
                appointment.setServiceProvider(post.getUser());
                appointment.setUser(user);
                appointment.setPost(post);
                appointment.setState("PENDING");
                appointment.setCreated(LocalDateTime.now());
                appointment.setUpdated(LocalDateTime.now());
                appointment.setAddress(address);
                try {
                    appointmentsService.create(appointment);
                    System.out.println("\nAppointment created");
                } catch (InvalidEntityDataException e) {
                    System.out.println("\n" + e.getMessage());
                }
            } else {
                System.out.println("\nYou have cancelled the appointment creation");
            }
        } else {
            System.out.println("You have already have pending appointment");
        }
    }
}
