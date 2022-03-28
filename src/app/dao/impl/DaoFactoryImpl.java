package app.dao.impl;


import app.dao.*;

public class DaoFactoryImpl implements DaoFactory {

    @Override
    public UserRepository createUserRepository() {
        return new UserRepositoryImpl();
    }

    @Override
    public CategoryRepository createCategoryRepository() {
        return new CategoryRepositoryImpl();
    }

    @Override
    public MessageRepository createMessageRepository() {
        return new MessageRepositoryImpl();
    }

    @Override
    public PostRepository createPostRepository() {
        return new PostRepositoryImpl();
    }

    @Override
    public RatingRepository createRatingRepository() {
        return new RatingRepositoryImpl();
    }

    @Override
    public AppointmentsRepository createAppointmentsRepository() {
        return new AppointmentsRepositoryImpl();
    }


}
