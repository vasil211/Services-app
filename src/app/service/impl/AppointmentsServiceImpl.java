package app.service.impl;

import app.dao.AppointmentsRepository;
import app.dao.CategoryRepository;
import app.exeption.InvalidEntityDataException;
import app.model.Appointments;
import app.model.Post;
import app.service.AppointmentsService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Scanner;

public class AppointmentsServiceImpl implements AppointmentsService {
    private final AppointmentsRepository appointmentsRepo;

    public AppointmentsServiceImpl(AppointmentsRepository appointmentsRepo) {
        this.appointmentsRepo = appointmentsRepo;
    }

    @Override
    public Appointments create(Appointments appointment) throws InvalidEntityDataException {
        if(appointment.getAddress().length() < 3) {
            throw new InvalidEntityDataException("Address cannot be empty!");
        }else {
            appointment.setState("PENDING");
            appointment.setCreated(LocalDateTime.now());
            return appointmentsRepo.create(appointment);
        }
    }

    @Override
    public boolean acceptAppointment(Long id) {
        return appointmentsRepo.acceptAppointment(id);
    }

    @Override
    public boolean declineAppointment(Long id) {
        return appointmentsRepo.declineAppointment(id);
    }

    @Override
    public boolean finishAppointment(Long id) {
        return appointmentsRepo.finishAppointment(id);
    }

    @Override
    public Collection<Appointments> findAll() {
        return appointmentsRepo.findAll();
    }

    @Override
    public Collection<Appointments> findAllPending(Long serviceProviderId) {
        return appointmentsRepo.findAllPending(serviceProviderId);
    }

    @Override
    public Collection<Appointments> findAllAccepted(Long serviceProviderId) {
        return appointmentsRepo.findAllAccepted(serviceProviderId);
    }

    @Override
    public Collection<Appointments> findAllDeclined(Long serviceProviderId) {
        return appointmentsRepo.findAllDeclined(serviceProviderId);
    }

    @Override
    public Collection<Appointments> findAllFinished(Long serviceProviderId) {
        return appointmentsRepo.findAllFinished(serviceProviderId);
    }

    @Override
    public Appointments findById(Long id) throws InvalidEntityDataException {
        var appointment = appointmentsRepo.findById(id);
        if(appointment == null){
            throw new InvalidEntityDataException("Appointment with id: " + id + " does not exist!");
        }
        return appointment;
    }

    @Override
    public boolean delete(Long id) {
        return appointmentsRepo.deleteById(id);
    }

    @Override
    public Long count() {
        return appointmentsRepo.count();
    }

    @Override
    public Long countFinishedForProvider(Long id) {
        return appointmentsRepo.countFinishedForProvider(id);
    }
}
