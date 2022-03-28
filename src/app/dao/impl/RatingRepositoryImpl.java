package app.dao.impl;

import app.dao.DaoFactory;
import app.dao.PostRepository;
import app.dao.RatingRepository;
import app.dao.UserRepository;
import app.model.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class RatingRepositoryImpl implements RatingRepository {

    private final Connection connection;

    public RatingRepositoryImpl() {
        connection = app.util.Database.getConnection();
    }

    DaoFactory daoFactory = new DaoFactoryImpl();
    UserRepository userRepository = daoFactory.createUserRepository();
    PostRepository postRepository = daoFactory.createPostRepository();

    @Override
    public Collection<Rating> findAll() {
        List<Rating> ratings = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.ratings");
            while (rs.next()) {
                Rating rating = new Rating();
                rating.setId(rs.getLong("id"));
                rating.setUserProvider(userRepository.findById(rs.getLong("service_provider_id")));
                rating.setUser(userRepository.findById(rs.getLong("user_id")));
                rating.setPost(postRepository.findById(rs.getLong("post_id")));
                rating.setRating(rs.getShort("email"));
                rating.setComment((rs.getString("first_name")));
                rating.setCreated(rs.getTimestamp("created").toLocalDateTime());
                ratings.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratings;
    }

    @Override
    public Collection<Rating> findAllForUser(Long id) {
        List<Rating> ratings = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from ratings where service_provider_id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Rating rating = new Rating();
                rating.setId(rs.getLong("id"));
                rating.setUserProvider(userRepository.findById(rs.getLong("service_provider_id")));
                rating.setUser(userRepository.findById(rs.getLong("user_id")));
                rating.setPost(postRepository.findById(rs.getLong("post_id")));
                rating.setRating(rs.getShort("email"));
                rating.setComment((rs.getString("first_name")));
                rating.setCreated(rs.getTimestamp("created").toLocalDateTime());
                ratings.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratings;
    }

    @Override
    public Rating findById(Long id) {
        Rating rating = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from ratings where id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                rating = new Rating();
                rating.setId(rs.getLong("id"));
                rating.setUserProvider(userRepository.findById(rs.getLong("service_provider_id")));
                rating.setUser(userRepository.findById(rs.getLong("user_id")));
                rating.setPost(postRepository.findById(rs.getLong("post_id")));
                rating.setRating(rs.getShort("email"));
                rating.setComment((rs.getString("first_name")));
                rating.setCreated(rs.getTimestamp("created").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rating;
    }

    @Override
    public Rating create(Rating rating) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into ratings(service_provider_id, user_id, post_id, rating, comment, created, modified) values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, rating.getUserProvider().getId());
            preparedStatement.setLong(2, rating.getUser().getId());
            preparedStatement.setLong(3, rating.getPost().getId());
            preparedStatement.setFloat(4, rating.getRating());
            preparedStatement.setString(5, rating.getComment());
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(rating.getCreated()));
            preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(rating.getModified()));
            preparedStatement.executeUpdate();
            return rating;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Rating update(Rating rating) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update ratings set service_provider_id=?, user_id=?, post_id= ?, rating = ?, comment= ?, created= ? where id= ?");
            preparedStatement.setLong(1, rating.getUserProvider().getId());
            preparedStatement.setLong(2, rating.getUser().getId());
            preparedStatement.setLong(3, rating.getPost().getId());
            preparedStatement.setFloat(4, rating.getRating());
            preparedStatement.setString(5, rating.getComment());
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(rating.getModified()));
            preparedStatement.setLong(7, rating.getId());
            preparedStatement.executeUpdate();
            return rating;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from ratings where id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long count() {
        long count = 0L;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select count(*) from ratings");
            rs.next();
            count = rs.getLong(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
    }

    @Override
    public Long countForUser(Long id) {
        long count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(id) from ratings where service_provider_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            count = rs.getLong(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
    }


    @Override
    public float calculateRating(Long id) {
        float count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select avg(rating) from ratings where service_provider_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            count = rs.getFloat(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
    }
}
