package app.dao;

public interface DaoFactory {

    UserRepository createUserRepository();
    CategoryRepository createCategoryRepository();
    MessageRepository createMessageRepository();
    PostRepository createPostRepository();
    RatingRepository createRatingRepository();
    AppointmentsRepository createAppointmentsRepository();
    ApplicationRepository createApplicationRepository();
}
