package app.service.impl;

import app.dao.AppointmentsRepository;
import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Appointments;
import app.service.AppointmentsService;

import java.time.LocalDateTime;
import java.util.Collection;

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
    public boolean declineAppointment(Long id, String reason) {
        return appointmentsRepo.declineAppointment(id, reason);
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
    public Collection<Appointments> findAllPendingForUser(Long serviceProviderId) throws NonexistingEntityException {
        var appointments=  appointmentsRepo.findAllPendingForUser(serviceProviderId);
        if(appointments.isEmpty()){
            throw new NonexistingEntityException("There are no pending appointments for this user");
        }
        return appointments;
    }

    @Override
    public Collection<Appointments> findAllAcceptedForUser(Long serviceProviderId) throws NonexistingEntityException {
        var appointments=  appointmentsRepo.findAllAcceptedForUser(serviceProviderId);
        if(appointments.isEmpty()){
            throw new NonexistingEntityException("There are no accepted appointments for this user");
        }
        return appointments;
    }

    @Override
    public Collection<Appointments> findAllDeclinedForUser(Long serviceProviderId) throws NonexistingEntityException {
        var appointments=  appointmentsRepo.findAllDeclinedForUser(serviceProviderId);
        if(appointments.isEmpty()){
            throw new NonexistingEntityException("There are no declined appointments for this user");
        }
        return appointments;
    }

    @Override
    public Collection<Appointments> findAllFinishedForUser(Long serviceProviderId) throws NonexistingEntityException {
        var appointments=  appointmentsRepo.findAllFinishedForUser(serviceProviderId);
        if(appointments.isEmpty()){
            throw new NonexistingEntityException("There are no finished appointments for this user");
        }
        return appointments;
    }

    @Override
    public Collection<Appointments> findAllPending() {
        return appointmentsRepo.findAllPending();
    }

    @Override
    public Collection<Appointments> findAllAccepted() {
        return appointmentsRepo.findAllAccepted();
    }

    @Override
    public Collection<Appointments> findAllDeclined() {
        return appointmentsRepo.findAllDeclined();
    }

    @Override
    public Collection<Appointments> findAllFinished() {
        return appointmentsRepo.findAllFinished();
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
