package app.dao.impl;

import app.dao.AppointmentsRepository;
import app.model.Appointments;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppointmentsRepositoryImpl implements AppointmentsRepository {
    private final Connection connection;
    private final UserRepositoryImpl userRepository = new UserRepositoryImpl();
    private final PostRepositoryImpl postRepository = new PostRepositoryImpl();
    public AppointmentsRepositoryImpl() {
        connection = app.util.Database.getConnection();
    }

    // Method to change the status of an appointment to "ACCEPTED" from database
    @Override
    public boolean acceptAppointment(Long id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update appointments set state = 'ACCEPTED' where id= ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to change the status of an appointment to "DECLINED" from database
    @Override
    public boolean declineAppointment(Long id, String reason) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update appointments set state = 'DECLINED', decline_comment = ? where id= ?");
            preparedStatement.setString(1, reason);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to change the status of an appointment to "FINISHED" from database
    @Override
    public boolean finishAppointment(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update appointments set state = 'FINISHED' where id= ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all appointments from database
    @Override
    public Collection<Appointments> findAll() {
        List<Appointments> appointments = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.appointments");
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    // Method to get appointment by id from database
    @Override
    public Appointments findById(Long id) {
        Appointments appointment = new Appointments();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE id= ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    // Method to create an appointment in database
    @Override
    public Appointments create(Appointments entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO services.appointments (service_provider_id, user_id, post_id, " +
                            "state, address, created, modified) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, entity.getServiceProvider().getId());
            preparedStatement.setLong(2, entity.getUser().getId());
            preparedStatement.setLong(3, entity.getPost().getId());
            preparedStatement.setString(4, entity.getState());
            preparedStatement.setString(5, entity.getAddress());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(entity.getCreated()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(entity.getUpdated()));
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

    // Method to update an appointment in database
    @Override
    public Appointments update(Appointments entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE services.appointments SET service_provider_id=?, user_id=?, post_id=?," +
                            " state=?, address=?, created=?, modified=? WHERE id=?");
            preparedStatement.setLong(1, entity.getServiceProvider().getId());
            preparedStatement.setLong(2, entity.getUser().getId());
            preparedStatement.setLong(3, entity.getPost().getId());
            preparedStatement.setString(4, entity.getState());
            preparedStatement.setString(5, entity.getAddress());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(entity.getCreated()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(entity.getUpdated()));
            preparedStatement.setLong(8, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    // Method to delete an appointment from database
    @Override
    public boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM services.appointments WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Method to count all appointments in database
    @Override
    public Long count() {
        Long count = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM services.appointments");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    // Method to count all "FINISHED" appointments in database for service provider
    @Override
    public Long countFinishedForProvider(Long serviceProviderId) {
        Long count = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM services.appointments WHERE service_provider_id = ? " +
                            "AND state = 'FINISHED'");
            preparedStatement.setLong(1, serviceProviderId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Method to get all appointments from database where service provider id is equal
    // to id and state is equal to "PENDING"
    @Override
    public Collection<Appointments> findAllPendingForUser(Long serviceProviderId) {
        Collection<Appointments> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE service_provider_id = ? AND state = 'PENDING'");
            preparedStatement.setLong(1, serviceProviderId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    // Method to get all appointments from database where service provider id is equal to id
    // and state is equal to "ACCEPTED"
    @Override
    public Collection<Appointments> findAllAcceptedForUser(Long serviceProviderId) {
        Collection<Appointments> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE service_provider_id = ? AND state = 'ACCEPTED'");
            preparedStatement.setLong(1, serviceProviderId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    // Method to get all appointments from database where service provider id is equal to id
    // and state is equal to "DECLINED"
    @Override
    public Collection<Appointments> findAllDeclinedForUser(Long serviceProviderId) {
        Collection<Appointments> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE service_provider_id = ? AND state = 'DECLINED'");
            preparedStatement.setLong(1, serviceProviderId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    // Method to get all appointments from database where service provider id is equal to id
    // and state is equal to "FINISHED"
    @Override
    public Collection<Appointments> findAllFinishedForUser(Long serviceProviderId) {
        Collection<Appointments> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE service_provider_id = ? AND state = 'FINISHED'");
            preparedStatement.setLong(1, serviceProviderId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Collection<Appointments> findAllPending() {
        Collection<Appointments> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE state = 'PENDING'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Collection<Appointments> findAllAccepted() {
        Collection<Appointments> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE state = 'ACCEPTED'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Collection<Appointments> findAllDeclined() {
        Collection<Appointments> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE state = 'DECLINED'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Collection<Appointments> findAllFinished() {
        Collection<Appointments> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM services.appointments WHERE state = 'FINISHED'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();
                appointment.setId(rs.getLong("id"));
                appointment.setServiceProvider(userRepository.findById(rs.getLong("service_provider_id")));
                appointment.setUser(userRepository.findById(rs.getLong("user_id")));
                appointment.setPost(postRepository.findById(rs.getLong("post_id")));
                appointment.setState(rs.getString("state"));
                appointment.setAddress(rs.getString("address"));
                appointment.setCreated(rs.getTimestamp("created").toLocalDateTime());
                appointment.setUpdated(rs.getTimestamp("modified").toLocalDateTime());
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

}
