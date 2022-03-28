package app;


import app.dao.*;
import app.dao.impl.DaoFactoryImpl;
import app.exeption.NonexistingEntityException;
import app.model.Post;
import app.model.Rating;
import app.model.User;
import app.service.*;
import app.service.impl.*;
import app.service.validators.PostValidation;
import app.service.validators.RatingValidation;
import app.service.validators.UserValidation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicReference;

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
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        PostService postService = new PostServiceImpl(postRepository, categoryRepository, postValidator);
        MessageService messageService = new MessageServiceImpl(messageRepository);
        RatingService ratingService = new RatingServiceImpl(ratingRepository, ratingValidator);
        AppointmentsService appointmentsService = new AppointmentsServiceImpl(appointmentsRepository);

        System.out.println("Getting all users:");
        userService.getAll().forEach(e -> System.out.println(e.toString()));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringJoiner sj = new StringJoiner("", "Messages: ", " ");

        var messages = messageService.messagesChat(3l, 4l);
        User user = new User();
        try {
            user = userService.getUserById(4l);
        } catch (NonexistingEntityException e) {
            e.printStackTrace();
        }
        for (var message : messages) {
            if (message.getSender() == 3l) {
                sj.add("\nMe: " + message.getMessage() + "\n" + message.getSent().format(formatter) + "\n");
            } else {
                sj.add("\n" + user.getFirstName() + " " + user.getLastName() + ":\n" + message.getMessage() + "\n" + "    " + message.getSent().format(formatter) + "\n");
            }
        }
        System.out.println("Messages between user and service provider:");
        System.out.println(sj);
        AtomicReference<Post> post = new AtomicReference<>(new Post());
        System.out.println("Getting all 'FINISHED' appointments:");
        appointmentsRepository.findAllFinished(3l).forEach(e -> {
            try {
                post.set(postService.getPostById(e.getPostId()));
            } catch (NonexistingEntityException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.toString() + " for job: " + post.get().getName());
        });
        User user1 = new User();
        User user2 = new User();
        try {
            user1 = userService.getUserById(4l);
        } catch (NonexistingEntityException e) {
            e.printStackTrace();
        }
        try {
            user2 = userService.getUserById(3l);
        } catch (NonexistingEntityException e) {
            e.printStackTrace();
        }
        Post post1 = new Post();
        try {
            post1 = postService.getPostById(1l);
        } catch (NonexistingEntityException e) {
            e.printStackTrace();
        }

        Rating rating = new Rating(1l, user1, user2, post1, (short) 0, "asdasdasdasdasdasdasdasdscaasdasawdadwadwadwadwadwdwadwaawdawfafwafwafwafdffdfdsdfssdfsdffsdsdfsdfsdsdggsdgsdgsdgsgsgffgfgfgfggfgffgfgfggfgfgfgfggfgfgfgfffdfdddgdgdgdasdasdasdasdasdasdasdasdasdwqdqwdwqdwqdqwdddwqdwqdqwdq  qwcewvc  crewrcwcwercwercewcewrcc  qwcrewcwcweqcewqcwewqrvtcqwrvctv  tccewcettwtcwqtecwqcqceqwtctqwcqcqwecttcqtctvvcqwecqewv   ctewcrewrcwr", LocalDateTime.now(), LocalDateTime.now());
        System.out.println("\n\nError creating rating when invalid rating is given:");
        ratingService.createRating(rating);
    }
}
