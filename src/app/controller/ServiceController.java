package app.controller;

import app.exeption.NonexistingEntityException;
import app.model.Category;
import app.model.Post;
import app.model.Rating;
import app.model.User;
import app.service.PostService;
import app.service.RatingService;
import app.view.Menu;
import app.view.PostView;
import app.view.RatingView;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceController {
    private final ArrayList<Post> posts;
    private final ArrayList<Category> categories;
    private final PostView postView;
    private final RatingService ratingService;
    private final RatingView ratingView;
    private final AppointmentController appointmentController;
    private final PostService postService;
    private final MessagesController messagesController;


    public ServiceController(ArrayList<Post> posts, ArrayList<Category> categories, PostView postView,
                             RatingService ratingService, RatingView ratingView,
                             AppointmentController appointmentController, PostService postService, MessagesController messagesController) {
        this.posts = posts;
        this.categories = categories;
        this.postView = postView;
        this.ratingService = ratingService;
        this.ratingView = ratingView;
        this.appointmentController = appointmentController;
        this.postService = postService;
        this.messagesController = messagesController;
    }

    public void servicesMenu(User user) {
        Scanner sc = new Scanner(System.in);
        // for user
        var menu = new Menu("Posts", List.of(
                new Menu.Option("Browse trough categories", () -> {
                    var count = 1;
                    System.out.println("");
                    for (var category : categories) {
                        System.out.println(count++ + " " + category.getName());
                    }
                    int id;
                    do {
                        System.out.println("Enter the number of the category you want to browse");
                        do {
                            String idStr = sc.nextLine();
                            try {
                                id = Integer.parseInt(idStr);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input");
                            }
                        } while (true);
                        if (id < 1 || id > categories.size()) {
                            System.out.println("Invalid number");
                        }
                    } while (id < 1 || id > categories.size());
                    int finalId = id;
                    var postsFromCategory = (ArrayList<Post>) posts.stream().filter(post -> post.getCategory().getId() == finalId)
                            .collect(Collectors.toList());
                    lookTroughPostsForUser(postsFromCategory, user);
                    return "";
                }),
                new Menu.Option("Search for service", () -> {
                    ArrayList<Post> postsFiltered = (ArrayList<Post>) searchForPost();
                    lookTroughPostsForUser(postsFiltered, user);
                    return "";
                }),
                new Menu.Option("List all posts", () -> {
                    posts.forEach(post -> {
                        postView.displayPost(post);
                        try {
                            System.out.println("Average rating: " + ratingService.calculateRatingForPost(post.getId()));
                            var ratings = ratingService.getAllRatingsForPost(post.getId());
                            ratings.forEach(ratingView::displayRating);
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    });
                    return "";
                })
        ));
        menu.show();
    }


    public void controllerForModerator() {
        Scanner sc = new Scanner(System.in);
        // for moderator
        var menu = new Menu("Posts", List.of(
                new Menu.Option("Browse trough categories", () -> {
                    var count = 1;
                    for (var category : categories) {
                        System.out.println(count++ + " " + category.getName());
                    }
                    int id;
                    do {
                        System.out.println("Enter the number of the category you want to browse");
                        do {
                            String idStr = sc.nextLine();
                            try {
                                id = Integer.parseInt(idStr);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input");
                            }
                        } while (true);
                        if (id < 1 || id > categories.size()) {
                            System.out.println("Invalid number");
                        }
                    } while (id < 1 || id > categories.size());
                    int finalId = id;
                    var postsFromCategory = (ArrayList<Post>) posts.stream().filter(post -> post.getCategory().getId() == finalId)
                            .collect(Collectors.toList());
                    lookTroughPostsForModerator(postsFromCategory);

                    return "";
                }),
                new Menu.Option("Search for service", () -> {
                    ArrayList<Post> postsFiltered = (ArrayList<Post>) searchForPost();
                    lookTroughPostsForModerator(postsFiltered);
                    return "";
                }),
                new Menu.Option("list all posts", () -> {
                    posts.forEach(post -> {
                        postView.displayPost(post);
                        try {
                            System.out.println("Average rating: " + ratingService.calculateRatingForPost(post.getId()));
                            var ratings = ratingService.getAllRatingsForPost(post.getId());
                            ratings.forEach(ratingView::displayRating);
                            System.out.println("\nAll posts with comments listed");
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    });
                    return "";
                })
        ));
        menu.show();
    }

    public void lookThroughPosts() {
        Scanner sc = new Scanner(System.in);
        // for anonymous user
        var menu = new Menu("Posts", List.of(
                new Menu.Option("Browse trough categories", () -> {
                    var count = 1;
                    for (var category : categories) {
                        System.out.println(count++ + " " + category.getName());
                    }
                    int id;
                    do {
                        System.out.println("Enter the number of the category you want to browse");
                        do {
                            String idStr = sc.nextLine();
                            try {
                                id = Integer.parseInt(idStr);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input");
                            }
                        } while (true);
                        if (id < 1 || id > categories.size()) {
                            System.out.println("Invalid number");
                        }
                    } while (id < 1 || id > categories.size());
                    int finalId = id;
                    var postsFromCategory = (ArrayList<Post>) posts.stream().filter(post -> post.getCategory().getId() == finalId)
                            .collect(Collectors.toList());
                    lookTroughPostsForAnonymous(postsFromCategory);
                    return "";
                }),
                new Menu.Option("Search for service", () -> {
                    ArrayList<Post> postsFiltered = (ArrayList<Post>) searchForPost();
                    lookTroughPostsForAnonymous(postsFiltered);
                    return "";
                }),
                new Menu.Option("list all posts", () -> {
                    posts.forEach(post -> {
                        postView.displayPost(post);
                        try {
                            System.out.println("Average rating: " + ratingService.calculateRatingForPost(post.getId()));
                            var ratings = ratingService.getAllRatingsForPost(post.getId());
                            ratings.forEach(ratingView::displayRating);
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    });
                    return "";
                })
        ));
        menu.show();
    }

    public Collection<Post> searchForPost() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the service you want to search for");
        String name;
        do {
            name = sc.nextLine();
            if (name.length() < 3 || name.length() > 20) {
                System.out.println("Name must be between 3 and 20 characters");
            }
        } while (name.length() < 3 || name.length() > 20);

        Collection<Post> filtered = new ArrayList<>();
        for (var post : posts) {
            if (post.getName().toLowerCase().contains(name.toLowerCase())) {
                filtered.add(post);
            }
        }
        return filtered;
    }

    private void lookTroughPostsForUser(ArrayList<Post> postsFiltered, User user) {
        Scanner sc = new Scanner(System.in);
        if (!postsFiltered.isEmpty()) {
            do {
                int count = postsFiltered.size();
                for (var post : postsFiltered) {
                    StringJoiner sj = new StringJoiner(" ", "\n", "");
                    sj.add("Post " + count-- + ":");
                    sj.add(post.getName());
                    sj.add("\nCategory: " + post.getCategory().getName());
                    sj.add("\nDescription: " + post.getInfo());
                    float rating = 0;
                    try {
                        rating = ratingService.calculateRatingForPost(post.getId());
                    } catch (NonexistingEntityException e) {
                        System.out.print("");
                    }
                    sj.add("\nRating: " + rating);
                    System.out.println(sj.toString());
                }

                System.out.println("\nEnter the number of the post you want to see: ");
                int choiceInt = 0;

                do {
                    do {
                        String idStr = sc.nextLine();
                        try {
                            choiceInt = Integer.parseInt(idStr);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input");
                        }
                    } while (true);
                    if (choiceInt < 1 || choiceInt > postsFiltered.size()) {
                        System.out.println("\nInvalid number, try again: ");
                    }
                } while (choiceInt < 1 || choiceInt > postsFiltered.size());
                postView.displayPost(postsFiltered.get(choiceInt - 1));
                ArrayList<Rating> comments = null;
                try {
                    comments = (ArrayList<Rating>) ratingService.getAllRatingsForPost(postsFiltered
                            .get(choiceInt - 1).getId());
                } catch (NonexistingEntityException e) {
                    System.out.println("No comments yet");
                }
                if (comments != null) {
                    comments.forEach(ratingView::displayRating);
                }
                int finalChoiceInt = choiceInt;
                var menu = new Menu("Post", List.of(
                        new Menu.Option("Create Appointment", () -> {
                            appointmentController.createAppointment(user, postsFiltered.get(finalChoiceInt - 1));
                            return "";
                        }),
                        new Menu.Option("Send message", () -> {
                            messagesController.sendMessage(user, postsFiltered.get(finalChoiceInt - 1));
                            return "";
                        })
                ));
                menu.show();

                System.out.println("\nDo you want to see open another post? (yes/no)");
                String choice2 = sc.next();
                if (choice2.equals("no")) break;
            } while (true);
        } else {
            System.out.println("No posts found");
        }
    }

    private void lookTroughPostsForModerator(ArrayList<Post> postsFiltered) {
        Scanner sc = new Scanner(System.in);
        if (!postsFiltered.isEmpty()) {
            do {
                int count = postsFiltered.size();
                for (var post : postsFiltered) {
                    StringJoiner sj = new StringJoiner(" ", "\n", "");
                    sj.add("Post " + count-- + ":");
                    sj.add(post.getName());
                    sj.add("\nCategory: " + post.getCategory().getName());
                    sj.add("\nDescription: " + post.getInfo());
                    float rating = 0;
                    try {
                        rating = ratingService.calculateRatingForPost(post.getId());
                    } catch (NonexistingEntityException e) {
                        System.out.print("");
                    }
                    sj.add("\nRating: " + rating);
                    System.out.println(sj.toString());
                }

                System.out.println("\nEnter the number of the post you want to see: ");
                int choiceInt = 0;

                do {
                    do {
                        String idStr = sc.nextLine();
                        try {
                            choiceInt = Integer.parseInt(idStr);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input");
                        }
                    } while (true);
                    if (choiceInt < 1 || choiceInt > postsFiltered.size()) {
                        System.out.println("\nInvalid number, try again: ");
                    }
                } while (choiceInt < 1 || choiceInt > postsFiltered.size());
                postView.displayPost(postsFiltered.get(choiceInt - 1));
                ArrayList<Rating> comments = null;
                try {
                    comments = (ArrayList<Rating>) ratingService.getAllRatingsForPost(postsFiltered
                            .get(choiceInt - 1).getId());
                } catch (NonexistingEntityException e) {
                    System.out.println("No comments yet");
                }
                if (comments != null) {
                    comments.forEach(ratingView::displayRating);
                }

                int caseInt = -1;
                System.out.println("\nMENU: ");
                System.out.println(" 1. Delete post");
                System.out.println(" 2. Exit");
                do {
                    try {
                        String idStr = sc.nextLine();
                        caseInt = Integer.parseInt(idStr);
                        if(caseInt < 1 || caseInt > 2) {
                            System.out.println("\nInvalid number, try again: ");
                        }else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input");
                    }
                } while (true);
                switch (caseInt) {
                    case 1:
                        try {
                            System.out.println("Type 'DELETE' to confirm: ");
                            String confirm = sc.nextLine();
                            if (confirm.equals("DELETE")) {
                                System.out.println("Enter explanation: ");
                                String explanation = sc.nextLine();
                                postService.deletePostById(postsFiltered.get(choiceInt - 1).getId(), explanation);
                                posts.remove(postsFiltered.get(choiceInt - 1));
                                postsFiltered.remove(choiceInt - 1);
                                System.out.println("Post deleted");
                            }
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        break;
                }
                if(postsFiltered.size() == 0) break;
                System.out.println("\nDo you want to see open another post? (yes/no)");
                String choice2 = sc.next();
                if (choice2.equals("no")) break;
            } while (true);
        } else {
            System.out.println("No posts found");
        }
    }

    public void lookTroughPostsForAnonymous(ArrayList<Post> postsFiltered) {
        Scanner sc = new Scanner(System.in);
        if (!postsFiltered.isEmpty()) {
            do {
                int count = postsFiltered.size();
                for (var post : postsFiltered) {
                    StringJoiner sj = new StringJoiner(" ", "\n", "");
                    sj.add("Post " + count-- + ":");
                    sj.add(post.getName());
                    sj.add("\nCategory: " + post.getCategory().getName());
                    sj.add("\nDescription: " + post.getInfo());
                    float rating = 0;
                    try {
                        rating = ratingService.calculateRatingForPost(post.getId());
                    } catch (NonexistingEntityException e) {
                        System.out.print("");
                    }
                    sj.add("\nRating: " + rating);
                    System.out.println(sj.toString());
                }

                System.out.println("\nEnter the number of the post you want to see: ");
                int choiceInt = 0;
                String choice = "";
                do {
                    do {
                        String idStr = sc.nextLine();
                        try {
                            choiceInt = Integer.parseInt(idStr);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input");
                        }
                    } while (true);
                    if (choiceInt < 1 || choiceInt > postsFiltered.size()) {
                        System.out.println("\nInvalid number, try again: ");
                    }
                } while (choiceInt < 1 || choiceInt > postsFiltered.size());
                postView.displayPost(postsFiltered.get(choiceInt - 1));
                ArrayList<Rating> comments = null;
                try {
                    comments = (ArrayList<Rating>) ratingService.getAllRatingsForPost(postsFiltered
                            .get(choiceInt - 1).getId());
                } catch (NonexistingEntityException e) {
                    System.out.println("No comments yet");
                }
                if (comments != null) {
                    comments.forEach(ratingView::displayRating);
                }
                System.out.println("\nDo you want to see open another post? (yes/no)");
                String choice2 = sc.next();
                if (choice2.equals("no")) break;
            } while (true);
        } else {
            System.out.println("No posts found");
        }
    }
}
