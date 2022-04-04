package app.dao.impl;

import app.dao.ApplicationRepository;
import app.model.Application;
import app.model.Appointments;
import app.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ApplicationRepositoryImpl implements ApplicationRepository {
    private final Connection connection;
    private final UserRepositoryImpl userRepository = new UserRepositoryImpl();

    public ApplicationRepositoryImpl() {
        connection = app.util.Database.getConnection();
    }

//    private Long id;
//    private User user;
//    private String reason;
//    private LocalDateTime created;
//    private String status;
//    private String reasonForRejection;
//    private LocalDateTime rejected;

    @Override
    public Collection<Application> findAll() {
        List<Application> applications = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.aplications where status = 'PENDING'");
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getLong("id"));
                application.setUser(userRepository.findById(rs.getLong("user_id")));
                application.setReason(rs.getString("info"));
                application.setCreated(rs.getTimestamp("created").toLocalDateTime());
                application.setStatus(rs.getString("status"));
                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    @Override
    public Collection<Application> findAllRejected() {
        List<Application> applications = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.aplications where status = 'REJECTED'");
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getLong("id"));
                application.setUser(userRepository.findById(rs.getLong("user_id")));
                application.setReason(rs.getString("info"));
                application.setCreated(rs.getTimestamp("created").toLocalDateTime());
                application.setStatus(rs.getString("status"));
                application.setReasonForRejection(rs.getString("reason_for_rejection"));
                application.setRejected(rs.getTimestamp("rejected").toLocalDateTime());
                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    // Method to update application status to approved
    @Override
    public void acceptApplication(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE services.aplications, services.users SET aplications.status = 'APPROVED'," +
                            "users.role = 'SERVICE_PROVIDER' WHERE aplications.id =  ? " +
                            "and users.id = aplications.user_id");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declineApplication(Long id, String reasonForDecline) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE services.aplications SET status = 'REJECTED',  reason_for_rejection = ? where id= ?");
            preparedStatement.setString(1, reasonForDecline);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Application getLastApplicationForUser(User user) {
        Application application = new Application();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.aplications WHERE user_id = ? ORDER BY created DESC LIMIT 1");
            preparedStatement.setLong(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                application.setId(rs.getLong("id"));
                application.setUser(userRepository.findById(rs.getLong("user_id")));
                application.setReason(rs.getString("info"));
                application.setCreated(rs.getTimestamp("created").toLocalDateTime());
                application.setStatus(rs.getString("status"));
                application.setReasonForRejection(rs.getString("reason_for_rejection"));
                application.setRejected(rs.getTimestamp("rejected").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return application;
    }

    @Override
    public Application findById(Long id) {
        Application application = new Application();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.aplications WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                application.setId(rs.getLong("id"));
                application.setUser(userRepository.findById(rs.getLong("user_id")));
                application.setReason(rs.getString("info"));
                application.setCreated(rs.getTimestamp("created").toLocalDateTime());
                application.setStatus(rs.getString("status"));
                application.setReasonForRejection(rs.getString("reason_for_rejection"));
                application.setRejected(rs.getTimestamp("rejected").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return application;
    }

    @Override
    public Application create(Application entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO services.aplications (user_id, info, created, status) " +
                            "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setString(2, entity.getReason());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getCreated()));
            preparedStatement.setString(4, entity.getStatus());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Application update(Application entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE services.aplications SET info = ?, created = ?, status = ?," +
                            " reason_for_rejection = ?, rejected = ? WHERE id = ?");
            preparedStatement.setString(1, entity.getReason());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getCreated()));
            preparedStatement.setString(3, entity.getStatus());
            preparedStatement.setString(4, entity.getReasonForRejection());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(entity.getRejected()));
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM services.aplications WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Long count() {
        long count = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM services.aplications");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


}
