package app.view;

import app.model.Application;
import app.model.User;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ApplicationView {

    public Application applyToBecomeProvider(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("You are applying to become a provider.");
        System.out.println("Please enter information for yourself, qualifications, and why are you applying." +
                " Max length is 100 characters.");
        String information;
        do {
            information = sc.nextLine();
            if (information.length() > 100) {
                System.out.println("Information is too long. Please try again.");
            }
        } while (information.length() > 500);
        Application application = new Application();
        application.setUser(user);
        application.setReason(information);
        application.setCreated(LocalDateTime.now());
        application.setStatus("PENDING");
        return application;
    }
}
