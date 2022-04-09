package app.controller;

import app.exeption.NonexistingEntityException;
import app.model.Appointments;
import app.model.Post;
import app.model.Rating;
import app.model.User;
import app.service.PostService;
import app.service.RatingService;
import app.view.Menu;
import app.view.RatingView;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class RatingsController {
    private final RatingService ratingService;
    private final PostService postService;
    private final RatingView ratingView;

    public RatingsController(RatingService ratingService, PostService postService, RatingView ratingView) {
        this.ratingService = ratingService;
        this.postService = postService;
        this.ratingView = ratingView;
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
                    } catch (Exception e) {
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
        for (var rating : ratings) {
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
        }
        ;
    }

    public void browseModeratedComments() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ratings: \n");
        var ratings = ratingService.getAllModerated();
        for (var rating : ratings) {
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
        }
        ;
    }

    public void openPostFromAppointment(ArrayList<Appointments> appointments) {
        Scanner sc = new Scanner(System.in);
        var menu = new Menu("", List.of(
                new Menu.Option("Open post from appointment", () -> {
                    String choice = "";
                    int choiceInt = 0;
                    do {
                        System.out.println("Enter the number of the appointment: ");
                        choice = sc.nextLine();
                        try {
                            choiceInt = Integer.parseInt(choice);
                            if (choiceInt < 1 || choiceInt > appointments.size()) {
                                System.out.println("Invalid number. <1/" + appointments.size() + ">");
                            } else {
                                var post = postService.getPostById(appointments.get(choiceInt - 1)
                                        .getPost().getId());
                                StringJoiner sj = new StringJoiner(" ", "\n", "");
                                sj.add("\nPost:");
                                sj.add("From: " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
                                sj.add("\nCategory: " + post.getCategory().getName());
                                sj.add("\nName: " + post.getName());
                                sj.add("\nInfo: " + post.getInfo());
                                sj.add("\nCreated: " + post.getCreated()
                                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
                                sj.add("Last modified: " + post.getModified()
                                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
                                System.out.println(sj);
                                ArrayList<Rating> comments = null;
                                try {
                                    comments = (ArrayList<Rating>) ratingService.getAllRatingsForPost(post.getId());
                                } catch (NonexistingEntityException e) {
                                    System.out.println("No comments yet");
                                }
                                if (comments != null) {
                                    comments.forEach(ratingView::displayRating);
                                }
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input");
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    } while (true);
                    return "";
                })
        ));
        menu.show();
    }

    public void openPostFromFinishedAppointment(ArrayList<Appointments> appointments, User user) {
        Scanner sc = new Scanner(System.in);
        var menu = new Menu("", List.of(
                new Menu.Option("Open post from appointment, and give options for rating the service", () -> {
                    String choice = "";
                    int choiceInt = 0;
                    do {
                        System.out.println("Enter the number of the appointment: ");
                        choice = sc.nextLine();
                        try {
                            choiceInt = Integer.parseInt(choice);
                            if (choiceInt < 1 || choiceInt > appointments.size()) {
                                System.out.println("Invalid number. <1/" + appointments.size() + ">");
                            } else {
                                var post = postService.getPostById(appointments.get(choiceInt - 1)
                                        .getPost().getId());
                                StringJoiner sj = new StringJoiner(" ", "\n", "");
                                sj.add("\nPost:");
                                sj.add("From: " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
                                sj.add("\nCategory: " + post.getCategory().getName());
                                sj.add("\nName: " + post.getName());
                                sj.add("\nInfo: " + post.getInfo());
                                sj.add("\nCreated: " + post.getCreated()
                                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
                                sj.add("Last modified: " + post.getModified()
                                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
                                System.out.println(sj);
                                var rating = ratingService.getRatingByPostIdFromUser(post.getId(), user.getId());
                                if (rating == null) {
                                    createReview(post, user);
                                }else {
                                    updateReview(rating);
                                }
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input");
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    } while (true);
                    return "";
                })
        ));
        menu.show();

    }

    private void updateReview(Rating rating) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nyour comment: " + rating.getComment());
        System.out.println("your rating: " + rating.getRating());
        String choice = "";
        System.out.println("Do you want to update your review? (yes/no)");
        choice = sc.nextLine();
        if (choice.equals("yes")) {
            Rating updated = ratingView.updateReview(rating);
            ratingService.updateRating(updated);
            System.out.println("Review updated");
        }
    }

    private void createReview(Post post, User user) {
        Scanner sc = new Scanner(System.in);
        String choice = "";
        System.out.println("\nDo you want to create review? (yes/no)");
        choice = sc.nextLine();
        if (choice.equals("yes")) {
            Rating created = ratingView.createReview(post, user);
            ratingService.createRating(created);
            System.out.println("Review created");
        }
    }
}

