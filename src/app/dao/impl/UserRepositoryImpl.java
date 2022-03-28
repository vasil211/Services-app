package app.dao.impl;

import app.dao.UserRepository;
import app.exeption.InvalidUserLoginExeption;
import app.model.Role;
import app.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class UserRepositoryImpl implements UserRepository {
    private final Connection connection;

    public UserRepositoryImpl() {
        connection = app.util.Database.getConnection();
    }

    @Override
    public boolean findUsername(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where user_name=?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public User login(String userName, String password) throws InvalidUserLoginExeption {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where user_name=?");
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (!password.equals(rs.getString("password"))) {
                    throw new InvalidUserLoginExeption("Wrong password");
                }
                user.setId(rs.getLong("id"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName((rs.getString("first_name")));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setLastOnline(rs.getTimestamp("last_online").toLocalDateTime());
                user.setModified(rs.getTimestamp("modified").toLocalDateTime());
                user.setCreated(rs.getTimestamp("created").toLocalDateTime());

            } else {
                throw new InvalidUserLoginExeption("Wrong username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean findEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName((rs.getString("first_name")));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setLastOnline(rs.getTimestamp("last_online").toLocalDateTime());
                user.setModified(rs.getTimestamp("modified").toLocalDateTime());
                user.setCreated(rs.getTimestamp("created").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.users");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName((rs.getString("first_name")));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setLastOnline(rs.getTimestamp("last_online").toLocalDateTime());
                user.setModified(rs.getTimestamp("modified").toLocalDateTime());
                user.setCreated(rs.getTimestamp("created").toLocalDateTime());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.users WHERE role = 'USER'");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName((rs.getString("first_name")));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setLastOnline(rs.getTimestamp("last_online").toLocalDateTime());
                user.setModified(rs.getTimestamp("modified").toLocalDateTime());
                user.setCreated(rs.getTimestamp("created").toLocalDateTime());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getAllServiceProviders() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.users WHERE role = 'SERVICE_PROVIDER'");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName((rs.getString("first_name")));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setLastOnline(rs.getTimestamp("last_online").toLocalDateTime());
                user.setModified(rs.getTimestamp("modified").toLocalDateTime());
                user.setCreated(rs.getTimestamp("created").toLocalDateTime());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getAllModerators() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM services.users WHERE role = 'MODERATOR'");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName((rs.getString("first_name")));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setLastOnline(rs.getTimestamp("last_online").toLocalDateTime());
                user.setModified(rs.getTimestamp("modified").toLocalDateTime());
                user.setCreated(rs.getTimestamp("created").toLocalDateTime());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getAllAdmins() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.users WHERE role = 'ADMIN'");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName((rs.getString("first_name")));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setLastOnline(rs.getTimestamp("last_online").toLocalDateTime());
                user.setModified(rs.getTimestamp("modified").toLocalDateTime());
                user.setCreated(rs.getTimestamp("created").toLocalDateTime());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public User create(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into users(role, user_name, password, email, first_name, last_name, phone, last_online, modified, created) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getRole().toString());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setString(7, user.getPhone());
            preparedStatement.setTimestamp(8, java.sql.Timestamp.valueOf(user.getLastOnline()));
            preparedStatement.setTimestamp(9, java.sql.Timestamp.valueOf(user.getModified()));
            preparedStatement.setTimestamp(10, java.sql.Timestamp.valueOf(user.getCreated()));
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateLastOnline(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update users set last_online = ?" + "where id= ?");
            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changePassword(String password, Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update users set password = ?" + "where id= ?");
            preparedStatement.setString(1, password);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User update(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update users set role=?, user_name=?, password= ?, email = ?, first_name= ?, last_name= ?, phone= ?, modified = ? where id= ?");
            preparedStatement.setString(1, user.getRole().toString());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setString(7, user.getPhone());
            preparedStatement.setTimestamp(8, java.sql.Timestamp.valueOf(user.getModified()));
            preparedStatement.setLong(9, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id=?");
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
            ResultSet rs = statement.executeQuery("select count(*) from users");
            rs.next();
            count = rs.getLong(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
    }


}
