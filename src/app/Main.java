package app;

import app.controller.*;
import app.dao.*;
import app.dao.impl.DaoFactoryImpl;
import app.service.*;
import app.service.impl.*;
import app.service.validators.CategoryValidation;
import app.service.validators.PostValidation;
import app.service.validators.RatingValidation;
import app.service.validators.UserValidation;
import app.view.ApplicationView;
import app.view.LoginView;
import app.view.MessagesView;
import app.view.PostView;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        DaoFactory daoFactory = new DaoFactoryImpl();
        UserRepository userRepository = daoFactory.createUserRepository();
        RatingRepository ratingRepository = daoFactory.createRatingRepository();
        CategoryRepository categoryRepository = daoFactory.createCategoryRepository();
        PostRepository postRepository = daoFactory.createPostRepository();
        MessageRepository messageRepository = daoFactory.createMessageRepository();
        PostValidation postValidator = new PostValidation();
        AppointmentsRepository appointmentsRepository = daoFactory.createAppointmentsRepository();
        UserValidation userValidator = new UserValidation(userRepository);
        RatingValidation ratingValidator = new RatingValidation(ratingRepository);
        UserService userService = new UserServiceImpl(userRepository, userValidator);
        LoginView loginVIew = new LoginView(userValidator);
        ApplicationView applicationView = new ApplicationView();
        ApplicationRepository applicationRepository = daoFactory.createApplicationRepository();
        ApplicationService applicationService = new ApplicationServiceImpl(applicationRepository);
        UserController adminController = new UserController(userValidator, userService, applicationView,
                applicationService);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        PostService postService = new PostServiceImpl(postRepository, categoryRepository, postValidator);
        MessageService messageService = new MessageServiceImpl(messageRepository);
        RatingService ratingService = new RatingServiceImpl(ratingRepository, ratingValidator);
        AppointmentsService appointmentsService = new AppointmentsServiceImpl(appointmentsRepository);
        CategoryValidation categoryValidator = new CategoryValidation(categoryService);
        AppointmentController appointmentController = new AppointmentController(appointmentsService, userService);
        RatingsController ratingsController = new RatingsController(ratingService);
        PostView postView = new PostView();
        PostsController postsController = new PostsController(categoryService, postService, userService,
                categoryValidator, ratingService, postView);
        MessagesView messagesView = new MessagesView(messageService);
        MessagesController messagesController = new MessagesController(messagesView, messageService);
        HomeController homeController = new HomeController(userService, adminController, appointmentsService,
                postService, postsController, appointmentController, ratingsController, messageService,
                messagesController);



//        var filter = FileSystems.getDefault().getPathMatcher("glob:*.{java}");
//        Path p1 = Paths.get(".");
//        try {
//            // how many line of java code we have written so far?
//            var numLines = Files.walk(p1)
//                    .filter(path -> Files.isRegularFile(path) && filter.matches(path.getFileName()))
//                    .mapToLong(path -> {
//                        try {
//                            return Files.lines(path).count();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return 0;
//                        }
//                    }).sum();
//            System.out.printf("Lines of conde in project: %d%n", numLines);
//
//            // which are the most used java keywords
//            var top20Kwywords = Files.walk(p1)
//                    .filter(path -> Files.isRegularFile(path) && filter.matches(path.getFileName()))
//                    .map(path -> {
//                        try {
//                            return Files.lines(path)
//                                    .flatMap(line -> Arrays.<String>stream(line.split("\\W+")))
//                                    .filter(word -> word.length() >= 2)
//                                    .collect(Collectors.groupingBy(
//                                            Function.identity(),
//                                            Collectors.counting())
//                                    );
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return Map.<String, Long>of();
//                        }
//                    }).flatMap(wordCounts -> wordCounts.entrySet().stream())
//                    .collect(Collectors.groupingBy(
//                            entry -> entry.getKey(),
//                            Collectors.summingLong(entry -> entry.getValue()))
//                    ).entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
//                    .limit(20)
//                    .collect(Collectors.toList());
//            System.out.println(top20Kwywords);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        var userController = new LoginController(userService, loginVIew, homeController);
        userController.login();
    }
}
