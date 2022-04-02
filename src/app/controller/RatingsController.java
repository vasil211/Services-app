package app.controller;

import app.exeption.NonexistingEntityException;
import app.model.Rating;
import app.service.RatingService;
import app.view.Menu;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class RatingsController {
    private final RatingService ratingService;

    public RatingsController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    public void adminRatingsMenu() {
        Scanner sc = new Scanner(System.in);
        var menu = new Menu("Ratings Menu", List.of(
                new Menu.Option("List all", () -> {
                    Collection<Rating> ratings = ratingService.getAll();
                    ratings.forEach(rating -> {
                        StringJoiner sj = new StringJoiner(", ", "Ratings: \n", "");
                        sj.add("\nFor post - ID: " + rating.getPost().getId()
                                + ", Name: " + rating.getPost().getName());
                        sj.add("\nFor user - ID: " + rating.getUserProvider().getId()
                                + ", Name: " + rating.getUserProvider().getFirstName()
                                + " " + rating.getUserProvider().getLastName());
                        sj.add("\nFrom user - ID: " + rating.getUser().getId()
                                + ", Name: " + rating.getUser().getFirstName()
                                + " " + rating.getUser().getLastName());
                        sj.add("\nComment: " + rating.getComment());
                        sj.add("\nRating: " + rating.getRating());
                        System.out.println(sj);
                    });
                    return "";
                }),
                new Menu.Option("List all Deleted", () -> {
                    Collection<Rating> ratings = ratingService.getAllDeleted();
                    ratings.forEach(rating -> {
                        StringJoiner sj = new StringJoiner(", ", "Ratings: \n", "");
                        sj.add("\nFor post - ID: " + rating.getPost().getId()
                                + ", Name: " + rating.getPost().getName());
                        sj.add("\nFor user - ID: " + rating.getUserProvider().getId()
                                + ", Name: " + rating.getUserProvider().getFirstName()
                                + " " + rating.getUserProvider().getLastName());
                        sj.add("\nFrom user - ID: " + rating.getUser().getId()
                                + ", Name: " + rating.getUser().getFirstName()
                                + " " + rating.getUser().getLastName());
                        sj.add("\nComment: " + rating.getComment());
                        sj.add("\nRating: " + rating.getRating());
                        System.out.println(sj);
                    });
                    return "";
                }),
                new Menu.Option("List all ratings for Post", () -> {
                    String idStr;
                    long id;
                    Collection<Rating> ratings;
                    System.out.println("Enter post id: ");
                    try {
                        idStr = sc.nextLine();
                        id = Long.parseLong(idStr);
                        ratings = ratingService.getAllRatingsForPost(id);
                        ratings.forEach(rating -> {
                            StringJoiner sj = new StringJoiner(", ", "Ratings for post "
                                    + rating.getPost().getName() + "\n", "");
                            sj.add("rating ID: " + rating.getId());
                            sj.add("\nFrom user - ID: " + rating.getUser().getId()
                                    + ", Name: " + rating.getUser().getFirstName()
                                    + " " + rating.getUser().getLastName());
                            sj.add("\nComment: " + rating.getComment());
                            sj.add("\nRating: " + rating.getRating());
                            System.out.println(sj);
                        });
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "";
                }),
                new Menu.Option("List all ratings for User", () -> {
                    String idStr;
                    long id;
                    Collection<Rating> ratings;
                    System.out.println("Enter user id: ");
                    try {
                        idStr = sc.nextLine();
                        id = Long.parseLong(idStr);
                        ratings = ratingService.getAllRatingsForUser(id);
                        ratings.forEach(rating -> {
                            StringJoiner sj = new StringJoiner(", ", "Ratings for User "
                                    + rating.getUserProvider().getFirstName() + " "
                                    + rating.getUserProvider().getLastName() + "\n", "");
                            sj.add("rating ID: " + rating.getId());
                            sj.add("\nFor post - ID: " + rating.getPost().getId()
                                    + ", Name: " + rating.getPost().getName());
                            sj.add("\nFrom user - ID: " + rating.getUser().getId()
                                    + ", Name: " + rating.getUser().getFirstName()
                                    + " " + rating.getUser().getLastName());
                            sj.add("\nComment: " + rating.getComment());
                            sj.add("\nRating: " + rating.getRating());
                            System.out.println(sj);
                        });
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "";
                }),
                new Menu.Option("List all ratings From User", () -> {
                    String idStr;
                    long id;
                    Collection<Rating> ratings;
                    System.out.println("Enter user id: ");
                    try {
                        idStr = sc.nextLine();
                        id = Long.parseLong(idStr);
                        ratings = ratingService.getAllRatingsFromUser(id);
                        ratings.forEach(rating -> {
                            StringJoiner sj = new StringJoiner(", ", "Ratings from User "
                                    + rating.getUser().getFirstName() + " "
                                    + rating.getUser().getLastName() + "\n", "");
                            sj.add("rating ID: " + rating.getId());
                            sj.add("\nFor post - ID: " + rating.getPost().getId()
                                    + ", Name: " + rating.getPost().getName());
                            sj.add("\nFor user - ID: " + rating.getUserProvider().getId()
                                    + ", Name: " + rating.getUserProvider().getFirstName()
                                    + " " + rating.getUserProvider().getLastName());
                            sj.add("\nComment: " + rating.getComment());
                            sj.add("\nRating: " + rating.getRating());
                            System.out.println(sj);
                        });
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "";
                }),
                new Menu.Option("Delete rating", () -> {
                    String idStr;
                    long id;
                    Rating rating;
                    System.out.println("Enter rating id: ");
                    try {
                        idStr = sc.nextLine();
                        id = Long.parseLong(idStr);
                        rating = ratingService.getRatingById(id);
                        StringJoiner sj = new StringJoiner(", ", "Rating to delete\n", "");
                        sj.add("\nFor post - ID: " + rating.getPost().getId()
                                + ", Name: " + rating.getPost().getName());
                        sj.add("\nFor user - ID: " + rating.getUserProvider().getId()
                                + ", Name: " + rating.getUserProvider().getFirstName()
                                + " " + rating.getUserProvider().getLastName());
                        sj.add("\nFrom user - ID: " + rating.getUser().getId()
                                + ", Name: " + rating.getUser().getFirstName()
                                + " " + rating.getUser().getLastName());
                        sj.add("\nComment: " + rating.getComment());
                        sj.add("\nRating: " + rating.getRating());
                        System.out.println(sj);
                        System.out.println("Type 'DELETE' to confirm: ");
                        String confirm = sc.nextLine();
                        if (confirm.equals("DELETE")) {
                            System.out.println("Enter explanation: ");
                            String explanation = sc.nextLine();
                            ratingService.deleteRatingById(id, explanation);
                            System.out.println("Rating deleted");
                        }
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "";
                })
        ));
        menu.show();
    }

    public void moderateCommentsMenu() {
        Scanner sc = new Scanner(System.in);
        var menu = new Menu("Ratings Menu", List.of(
                new Menu.Option("Browse un-moderated comments", () -> {
                    try {
                        browseUnmoderatedComments();
                    }catch (Exception e){
                        System.out.println("");
                    }
                    return "";
                }),
                new Menu.Option("Browse moderated comments", () -> {
                    browseModeratedComments();
                    return "";
                }),
                new Menu.Option("List deleted comments", () -> {
                    ratingService.getAllDeleted().forEach(rating -> {
                        StringJoiner sj = new StringJoiner(", ", "\n", "");
                        sj.add("\nID: " + rating.getId());
                        sj.add("\nFor post - ID: " + rating.getPost().getId());
                        sj.add("\nFor user - ID: " + rating.getUserProvider().getId());
                        sj.add("\nFrom user - ID: " + rating.getUser().getId());
                        sj.add("\nComment: " + rating.getComment());
                        sj.add("\nRating: " + rating.getRating());
                        sj.add("\nDeleted on: " + rating.getModified()
                                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
                        sj.add("\nReason for deletion: " + rating.getDeletedReason());
                        System.out.println(sj);
                    });
                    return "";
                })
        ));
        menu.show();
    }

    public void browseUnmoderatedComments() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ratings: \n");
        var ratings = ratingService.getAllUnmoderated();
        for(var rating : ratings){
            StringJoiner sj = new StringJoiner(", ", "\n", "");
            sj.add("\nID: " + rating.getId());
            sj.add("\nFor post - ID: " + rating.getPost().getId());
            sj.add("\nFor user - ID: " + rating.getUserProvider().getId());
            sj.add("\nFrom user - ID: " + rating.getUser().getId());
            sj.add("\nComment: " + rating.getComment());
            sj.add("\nRating: " + rating.getRating());
            sj.add("\nCreated: " + rating.getCreated().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            System.out.println(sj);
            System.out.println("");
            var menu = new Menu("Browse un-moderated comments", List.of(
                    new Menu.Option("Go to next, and mark as moderated", () -> {
                        System.out.println("Do you approve this comment? (yes/no)");
                        String answer = sc.nextLine();
                        if (answer.equals("yes")) {
                            ratingService.markAsModerated(rating.getId());
                            return "";
                        }
                        return "Comment skipped";
                    }),
                    new Menu.Option("Delete comment, and add explanation", () -> {
                        try {
                            System.out.println("Type 'DELETE' to confirm: ");
                            String confirm = sc.nextLine();
                            if (confirm.equals("DELETE")) {
                                System.out.println("Enter explanation: ");
                                String explanation = sc.nextLine();
                                ratingService.deleteRatingById(rating.getId(), explanation);
                                System.out.println("Rating deleted");
                            }
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                        return "";
                    })
            ));
            var check = menu.showForForEach();
            if (check) break;
        };
    }

    public void browseModeratedComments() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ratings: \n");
        var ratings = ratingService.getAllModerated();
        for(var rating : ratings){
            StringJoiner sj = new StringJoiner(", ", "\n", "");
            sj.add("\nID: " + rating.getId());
            sj.add("\nFor post - ID: " + rating.getPost().getId());
            sj.add("\nFor user - ID: " + rating.getUserProvider().getId());
            sj.add("\nFrom user - ID: " + rating.getUser().getId());
            sj.add("\nComment: " + rating.getComment());
            sj.add("\nRating: " + rating.getRating());
            sj.add("\nCreated: " + rating.getCreated().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            System.out.println(sj);
            System.out.println("");
            var menu = new Menu("Browse moderated comments", List.of(
                    new Menu.Option("Go to next, and mark as moderated", () -> {
                        ratingService.markAsModerated(rating.getId());
                        return "";
                    }),
                    new Menu.Option("Delete comment, and add explanation", () -> {
                        try {
                            System.out.println("Type 'DELETE' to confirm: ");
                            String confirm = sc.nextLine();
                            if (confirm.equals("DELETE")) {
                                System.out.println("Enter explanation: ");
                                String explanation = sc.nextLine();
                                ratingService.deleteRatingById(rating.getId(), explanation);
                                System.out.println("Rating deleted");
                            }
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                        return "";
                    })
            ));
            var check = menu.showForForEach();
            if (check) break;
        };
    }
}

