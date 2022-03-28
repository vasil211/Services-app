package app.dao.impl;

import app.dao.*;
import app.model.Message;
import app.model.MessagesGroupedByUser;
import app.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MessageRepositoryImpl implements MessageRepository {

    private final Connection connection;

    public MessageRepositoryImpl() {
        connection = app.util.Database.getConnection();
    }

    DaoFactory daoFactory = new DaoFactoryImpl();
    UserRepository userRepository = daoFactory.createUserRepository();
    PostRepository postRepository = daoFactory.createPostRepository();

    @Override
    public Collection<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.messages");
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getLong("id"));
                message.setSender(rs.getLong("sender_id"));
                message.setUserProvider(userRepository.findById(rs.getLong("service_provider_id")));
                message.setUser(userRepository.findById(rs.getLong("user_id")));
                message.setPost(postRepository.findById(rs.getLong("post_id")));
                message.setMessage(rs.getString("message"));
                message.setSent(rs.getTimestamp("sent").toLocalDateTime());
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message create(Message message) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into messages(sender_id, service_provider_id, user_id, post_id, message, sent) values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, message.getSender());
            preparedStatement.setLong(2, message.getUserProvider().getId());
            preparedStatement.setLong(3, message.getUser().getId());
            preparedStatement.setLong(4, message.getPost().getId());
            preparedStatement.setString(5, message.getMessage());
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(message.getSent()));
            preparedStatement.executeUpdate();
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<MessagesGroupedByUser> messagesGroupedForUser(Long provider_id, Long user_id) {
        List<MessagesGroupedByUser> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT users.first_name, users.last_name, messages.sent, messages.user_id, messages.service_provider_id, posts.name FROM messages join users on messages.service_provider_id = users.id join posts on messages.post_id = posts.id  where messages.service_provider_id = ?  group by user_id  ");
            preparedStatement.setLong(1, provider_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                MessagesGroupedByUser message = new MessagesGroupedByUser();
                message.setId(rs.getLong("id"));
                message.setSent(rs.getTimestamp("sent").toLocalDateTime());
                message.setFirstName(rs.getString("first_name"));
                message.setLastName(rs.getString("last_name"));
                message.setSent(rs.getTimestamp("sent").toLocalDateTime());
                message.setUserId(rs.getLong("user_id"));
                message.setService_providerId(rs.getLong("service_provider_id"));
                message.setName(rs.getString("name"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Collection<MessagesGroupedByUser> messagesGroupedForProvider(Long provider_id, Long user_id) {
        List<MessagesGroupedByUser> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT users.first_name, users.last_name, messages.sent, messages.user_id, messages.service_provider_id, posts.name FROM messages join users on messages.service_provider_id = users.id join posts on messages.post_id = posts.id  where messages.user_id = ?  group by messages.service_provider_id");
            preparedStatement.setLong(1, user_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                MessagesGroupedByUser message = new MessagesGroupedByUser();
                message.setId(rs.getLong("id"));
                message.setFirstName(rs.getString("first_name"));
                message.setLastName(rs.getString("last_name"));
                message.setSent(rs.getTimestamp("sent").toLocalDateTime());
                message.setUserId(rs.getLong("user_id"));
                message.setService_providerId(rs.getLong("service_provider_id"));
                message.setName(rs.getString("name"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
    @Override
    public Collection<Message> messagesChat(Long provider_id, Long user_id) {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from messages where service_provider_id = ? and user_id = ? order by sent");
            preparedStatement.setLong(1, provider_id);
            preparedStatement.setLong(2, user_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getLong("id"));
                message.setSender(rs.getLong("sender_id"));
                message.setUserProvider(userRepository.findById(rs.getLong("service_provider_id")));
                message.setUser(userRepository.findById(rs.getLong("user_id")));
                message.setPost(postRepository.findById(rs.getLong("post_id")));
                message.setMessage(rs.getString("message"));
                message.setSent(rs.getTimestamp("sent").toLocalDateTime());
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    // Method to get count of messages for a user from service provider
    @Override
    public Long countMessagesForUsers(Long user_id, Long service_provider_id) {
        long count = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from messages where user_id = ? and service_provider_id = ?");
            preparedStatement.setLong(1, user_id);
            preparedStatement.setLong(2, service_provider_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Message findById(Long id) {
        return null;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return true;
    }

    @Override
    public Long count() {
        return null;
    }
}
