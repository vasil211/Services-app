package app.dao.impl;

import app.dao.CategoryRepository;
import app.dao.DaoFactory;
import app.dao.PostRepository;
import app.dao.UserRepository;
import app.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class PostRepositoryImpl implements PostRepository {

    private final Connection connection;

    public PostRepositoryImpl() {
        connection = app.util.Database.getConnection();
    }

    DaoFactory daoFactory = new DaoFactoryImpl();
    UserRepository userRepository = daoFactory.createUserRepository();
    CategoryRepository categoryRepository = daoFactory.createCategoryRepository();

    @Override
    public Collection<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.posts");
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setUser(userRepository.findById(rs.getLong("user_id")));
                post.setCategory(categoryRepository.findById(rs.getLong("category_id")));
                post.setName(rs.getString("name"));
                post.setInfo(rs.getString("info"));
                post.setCreated(rs.getTimestamp("created").toLocalDateTime());
                post.setModified(rs.getTimestamp("modified").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(Long id) {
        Post post = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from posts where id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                post = new Post();
                post.setId(rs.getLong("id"));
                post.setUser(userRepository.findById(rs.getLong("user_id")));
                post.setCategory(categoryRepository.findById(rs.getLong("category_id")));
                post.setName(rs.getString("name"));
                post.setInfo(rs.getString("info"));
                post.setModified(rs.getTimestamp("modified").toLocalDateTime());
                post.setCreated(rs.getTimestamp("created").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post create(Post post) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into posts(user_id, category_id, name, info, created, modified) values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, post.getUser().getId());
            preparedStatement.setLong(2, post.getCategory().getId());
            preparedStatement.setString(3, post.getName());
            preparedStatement.setString(4, post.getInfo());
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(post.getCreated()));
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(post.getModified()));
            preparedStatement.executeUpdate();
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Post update(Post post) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update posts set user_id=?, category_id=?, name= ?, info = ?, created= ?, modified= ? where id= ?");
            preparedStatement.setLong(1, post.getUser().getId());
            preparedStatement.setLong(2, post.getCategory().getId());
            preparedStatement.setString(3, post.getName());
            preparedStatement.setString(4, post.getInfo());
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(post.getCreated()));
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(post.getModified()));
            preparedStatement.setLong(7, post.getId());
            preparedStatement.executeUpdate();
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from posts where id=?");
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
            ResultSet rs = statement.executeQuery("select count(*) from posts");
            rs.next();
            count = rs.getLong(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
    }

    @Override
    public Collection<Post> findByCategory(Long id) {
        List<Post> posts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from posts where category_id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setUser(userRepository.findById(rs.getLong("user_id")));
                post.setCategory(categoryRepository.findById(rs.getLong("category_id")));
                post.setName(rs.getString("name"));
                post.setInfo(rs.getString("info"));
                post.setCreated(rs.getTimestamp("created").toLocalDateTime());
                post.setModified(rs.getTimestamp("modified").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // Method for getting all posts from database where user_id is equal to id
    @Override
    public Collection<Post> getAllPostsByUser(Long id) {
        List<Post> posts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from posts where user_id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setUser(userRepository.findById(rs.getLong("user_id")));
                post.setCategory(categoryRepository.findById(rs.getLong("category_id")));
                post.setName(rs.getString("name"));
                post.setInfo(rs.getString("info"));
                post.setCreated(rs.getTimestamp("created").toLocalDateTime());
                post.setModified(rs.getTimestamp("modified").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
    // Method for getting all posts from database where category_id is equal to id
    @Override
    public Collection<Post> getAllPostsByCategory(Long id) {
        List<Post> posts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from posts where category_id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setUser(userRepository.findById(rs.getLong("user_id")));
                post.setCategory(categoryRepository.findById(rs.getLong("category_id")));
                post.setName(rs.getString("name"));
                post.setInfo(rs.getString("info"));
                post.setCreated(rs.getTimestamp("created").toLocalDateTime());
                post.setModified(rs.getTimestamp("modified").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
    // Method to count all posts from database where category_id is equal to id
    @Override
    public Long countPostsByCategory(Long id) {
        long count = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from posts where category_id=?");
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

    // Method to count all posts from database where user_id is equal to id
    @Override
    public Long countPostsByUser(Long id) {
        long count = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from posts where user_id=?");
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

    // Method to calculate average rating from database for post with id
    @Override
    public float calculateRatingForPost(Long id) {
        float rating = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select avg(rating) from ratings where post_id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            rating = rs.getFloat(1);
            return rating;
        } catch (SQLException e) {
            e.printStackTrace();
            return rating;
        }
    }

}
