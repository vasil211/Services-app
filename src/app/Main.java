package app;

import app.controller.*;
import app.dao.*;
import app.dao.impl.DaoFactoryImpl;
import app.model.Category;
import app.model.Post;
import app.service.*;
import app.service.impl.*;
import app.service.validators.CategoryValidation;
import app.service.validators.PostValidation;
import app.service.validators.RatingValidation;
import app.service.validators.UserValidation;
import app.view.*;

import java.util.ArrayList;

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
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        PostService postService = new PostServiceImpl(postRepository, categoryRepository, postValidator);
        MessageService messageService = new MessageServiceImpl(messageRepository);
        RatingService ratingService = new RatingServiceImpl(ratingRepository, ratingValidator);
        AppointmentsService appointmentsService = new AppointmentsServiceImpl(appointmentsRepository);
        CategoryValidation categoryValidator = new CategoryValidation(categoryService);
        RatingView ratingView = new RatingView();
        RatingsController ratingsController = new RatingsController(ratingService, postService, ratingView);
        AppointmentController appointmentController = new AppointmentController(appointmentsService, userService,
                ratingsController);
        PostView postView = new PostView();
        RegistrationView registrationView = new RegistrationView(userValidator);
        RegistrationController registrationController = new RegistrationController(registrationView, userService);
        ArrayList<Post> posts = (ArrayList<Post>) postService.getAll();
        ArrayList<Category> categories = (ArrayList<Category>) categoryService.getAllCategories();
        ServiceController serviceController = new ServiceController(posts, categories, postView, ratingService,
                ratingView, appointmentController, postService);
        PostsController postsController = new PostsController(categoryService, postService, userService,
                categoryValidator, ratingService, postView, serviceController);
        MessagesView messagesView = new MessagesView(messageService);
        ApplicationController applicationController = new ApplicationController(applicationService);
        MessagesController messagesController = new MessagesController(messagesView, messageService);
        UserController adminController = new UserController(userValidator, userService, applicationView,
                applicationService, registrationController);
        HomeController homeController = new HomeController(userService, adminController, appointmentsService,
                postService, postsController, appointmentController, ratingsController, messageService,
                messagesController, applicationController, serviceController);
        LoginController loginController = new LoginController(userService, loginVIew, homeController);
        MainController mainController = new MainController(loginController, registrationController, serviceController);


        mainController.main();
    }
}
