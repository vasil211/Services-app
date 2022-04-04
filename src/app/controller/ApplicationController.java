package app.controller;

import app.model.Message;
import app.service.ApplicationService;
import app.view.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    public void moderatorApplicationsMenu() {
        var menu = new Menu("Applications", List.of(
                new Menu.Option("Review applications", () -> {
                    applicationsReview();
                    return "";
                }),
                new Menu.Option("List all rejected", () -> {
                    listAllRejected();
                    return "";
                })
        ));
        menu.show();
    }

    public void applicationsReview() {
        Scanner sc = new Scanner(System.in);

        var applications = applicationService.getAllApplications();
        if (!applications.isEmpty()) {
            for (var application : applications) {
                StringJoiner sj = new StringJoiner(" ", "\n", "");
                sj.add("Application ID: " + application.getId());
                sj.add("\nDate: " + application.getCreated().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                sj.add("\nFrom: " + application.getUser().getFirstName() + " " + application.getUser().getLastName());
                sj.add("\nInfo: \n" + application.getReason());
                System.out.println(sj.toString());
                var menu = new Menu("Application", List.of(
                        new Menu.Option("Accept", () -> {
                            applicationService.acceptApplication(application.getId());
                            System.out.println("Application accepted");
                            return "";
                        }),
                        new Menu.Option("Reject", () -> {
                            String explanation = "";
                            do {
                                System.out.println("Enter reason for rejection");
                                explanation = sc.nextLine();
                                if (explanation.length() > 250) {
                                    System.out.println("Message is too long. Max length is 250 characters");
                                }
                            } while (explanation.length() > 250);
                            applicationService.declineApplication(application.getId(), explanation);
                            System.out.println("Application rejected");
                            return "";
                        })
                ));
                var check = menu.showForForEach();
                if (check) break;
            }
        } else {
            System.out.println("No applications");
        }
    }

    public void listAllRejected() {

    }
}
