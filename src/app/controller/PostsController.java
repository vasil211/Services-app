package app.controller;

import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Category;
import app.model.Post;
import app.model.Rating;
import app.model.User;
import app.service.CategoryService;
import app.service.PostService;
import app.service.RatingService;
import app.service.UserService;
import app.service.validators.CategoryValidation;
import app.view.Menu;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PostsController {
    private final CategoryService categoryService;
    private final PostService postService;
    private final UserService userService;
    private final CategoryValidation categoryValidation;
    private final RatingService ratingService;

    public PostsController(CategoryService categoryService, PostService postService,
                           UserService userService, CategoryValidation categoryValidation,
                           RatingService ratingService) {
        this.categoryService = categoryService;
        this.postService = postService;
        this.userService = userService;
        this.categoryValidation = categoryValidation;
        this.ratingService = ratingService;
    }

    public void adminPostsMenu() {
        Scanner scanner = new Scanner(System.in);
        var menu = new Menu("Posts Menu", List.of(
                new Menu.Option("List all Categories", () -> {
                    categoryService.getAllCategories().forEach(System.out::println);
                    return "";
                }),
                new Menu.Option("Search for Category and list its posts", () -> {
                    Category category;
                    String categoryName;
                    do {
                        try {
                            System.out.println("Enter the name of the category you want to search for:");
                            categoryName = scanner.nextLine();
                            category = categoryService.getCategoryByName(categoryName);
                            break;
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Try again");
                        }
                    } while (true);
                    Collection<Post> posts = new ArrayList<>();
                    try {
                        posts = postService.getAllPostsByCategory(category.getId());
                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }

                    posts.forEach(post -> {

                        var average = postService.calculateRatingForPost(post.getId());
                        StringJoiner sj = new StringJoiner(", ", "", "");
                        sj.add("id: " + post.getId().toString());
                        sj.add("name: " + post.getName());
                        sj.add("user: " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
                        sj.add("rating: " + average);
                        sj.add("\ncreated: " + post.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        sj.add("last modified: " + post.getModified().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        System.out.println("Posts: ");
                        System.out.println(sj);
                    });
                    return "";
                }),
                new Menu.Option("Update Category's name", () -> {
                    String categoryName = null;
                    Category category;
                    do {
                        try {
                            System.out.println("Enter name of Category you want to update:");
                            categoryName = scanner.nextLine();
                            category = categoryService.getCategoryByName(categoryName);
                            break;
                        } catch (NonexistingEntityException e) {
                            System.out.println(e.getMessage());
                        }
                    } while (true);
                    System.out.println("Category's name: " + category.getName());
                    do {
                        System.out.println("Enter new name for the category:");
                        String newName = scanner.nextLine();
                        try {
                            categoryValidation.isValidCategory(newName);
                            category.setName(newName);
                            categoryService.updateCategory(category);
                            break;
                        } catch (InvalidEntityDataException e) {
                            System.out.println(e.getMessage());
                        } catch (NonexistingEntityException e) {
                            e.printStackTrace();
                        }
                    } while (true);

                    return "";
                }),
                new Menu.Option("Delete Category", () -> {
                    String idStr;
                    long id;
                    System.out.println("YOU WILL DELETE ALL POSTS IN THIS CATEGORY BY DELETING THIS CATEGORY!!!");
                    System.out.println("type 'i understand' to continue");
                    String answer = scanner.nextLine();
                    if (answer.equals("i understand")) {
                        return "";
                    }
                    try {
                        System.out.println("Enter id of category you want to delete:");
                        idStr = scanner.nextLine();
                        id = Long.parseLong(idStr);
                        categoryService.deleteCategoryById(id);
                    } catch (NumberFormatException | NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "";
                }),
                new Menu.Option("List all Posts, without description", () -> {

                    var posts = postService.getAll();
                    posts.forEach(post -> {
                        var average = postService.calculateRatingForPost(post.getId());
                        StringJoiner sj = new StringJoiner(", ", "", "");
                        sj.add("id: " + post.getId().toString());
                        sj.add("category: " + post.getCategory().getName());
                        sj.add("name: " + post.getName());
                        sj.add("user: " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
                        sj.add("rating: " + average);
                        sj.add("\ncreated: " + post.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        sj.add("last modified: " + post.getModified().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        System.out.println("Posts: ");
                        System.out.println(sj);
                    });
                    return "";
                }),
                new Menu.Option("List all Providers and their rating and number of posts", () -> {
                    var users = userService.getAllServiceProviders();
                    users.forEach(user -> {
                        Long post = 0L;
                        Long postCount = 0L;
                        try {
                            post = postService.countFromUser(user.getId());
                            postCount = postService.countFromUser(user.getId());
                        } catch (NonexistingEntityException e) {
                            System.out.print("");
                        }
                        if (post != 0) {
                            var rating = postService.calculateRatingForUser(user.getId());

                            StringJoiner sj = new StringJoiner(", ", "", "");
                            sj.add("id: " + user.getId().toString());
                            sj.add("name: " + user.getFirstName() + " " + user.getLastName());
                            sj.add("number of posts: " + postCount);
                            sj.add("rating: " + rating);
                            sj.add("\ncreated: " + user.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                            sj.add("last modified: " + user.getModified().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                            System.out.println(sj);
                        }
                    });
                    return "";
                }),
                new Menu.Option("Get User's posts with comments", () -> {
                    String idStr;
                    Long id = 0L;
                    Collection<Post> posts = new ArrayList<>();
                    Collection<Rating> ratings = new ArrayList<>();
                    User user = null;
                    try {
                        System.out.println("Enter id of user you want to see posts:");
                        idStr = scanner.nextLine();
                        id = Long.parseLong(idStr);
                        user = userService.getUserById(id);
                        posts = postService.getAllPostsByUser(id);

                    } catch (NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        ratings = ratingService.getAllForUser(id);
                    } catch (NonexistingEntityException e) {
                        System.out.println("");
                    }
                    String names = new String(user.getFirstName() + " " + user.getLastName());
                    Collection<Rating> finalRatings = ratings;
                    posts.forEach(post -> {
                        StringJoiner sj = new StringJoiner(", ", "Posts for user " + names, "");
                        sj.add("\npost:");
                        sj.add("id: " + post.getId().toString());
                        sj.add("category: " + post.getCategory().getName());
                        sj.add("name: " + post.getName());
                        sj.add("\ninfo: " + post.getInfo());
                        sj.add("\ncreated: " + post.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        sj.add("last modified: " + post.getModified().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        sj.add("\ncomments:");
                        if (finalRatings.size() != 0) {
                            finalRatings.forEach(rating -> {
                                if (rating.getPost().getId() == post.getId()) {
                                    sj.add("\ncomment by: " + rating.getUser().getFirstName() + " " + rating.getUser().getLastName());
                                    sj.add("\ncomment: " + rating.getComment());
                                    sj.add("\nrating: " + rating.getRating());
                                }
                            });
                        } else {
                            sj.add("No comments");
                        }
                        System.out.println(sj);
                    });
                    return "";
                }),
                new Menu.Option("Delete post", () -> {
                    String idStr;
                    long id;
                    System.out.println("YOU WILL DELETE ALL RATINGS AND COMMENTS IN THIS POST BY DELETING IT!!!");
                    System.out.println("type 'i understand' to continue");
                    String answer = scanner.nextLine();
                    if (answer.equals("i understand")) {
                        return "";
                    }
                    try {
                        System.out.println("Enter id of post you want to delete:");
                        idStr = scanner.nextLine();
                        id = Long.parseLong(idStr);
                        postService.deletePostById(id);
                    } catch (NumberFormatException | NonexistingEntityException e) {
                        System.out.println(e.getMessage());
                    }
                    return "";
                })
        ));
        menu.show();
    }
}
