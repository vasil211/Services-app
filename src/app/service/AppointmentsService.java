package app.service;

import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Appointments;
import app.model.Post;

import java.util.Collection;

public interface AppointmentsService {
    boolean acceptAppointment(Long id);
    boolean declineAppointment(Long id);
    boolean finishAppointment(Long id);
    Collection<Appointments> findAll();
    Collection<Appointments> findAllPendingForUser(Long serviceProviderId) throws NonexistingEntityException;
    Collection<Appointments> findAllAcceptedForUser(Long serviceProviderId) throws NonexistingEntityException;
    Collection<Appointments> findAllDeclinedForUser(Long serviceProviderId) throws NonexistingEntityException;
    Collection<Appointments> findAllFinishedForUser(Long serviceProviderId) throws NonexistingEntityException;
    Collection<Appointments> findAllPending();
    Collection<Appointments> findAllAccepted();
    Collection<Appointments> findAllDeclined();
    Collection<Appointments> findAllFinished();
    Appointments findById(Long id) throws InvalidEntityDataException;
    Appointments create(Appointments appointments) throws InvalidEntityDataException;
    boolean delete(Long id);
    Long count();
    Long countFinishedForProvider(Long id);

}
