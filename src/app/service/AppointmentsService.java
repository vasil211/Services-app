package app.service;

import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Appointments;

import java.util.Collection;

public interface AppointmentsService {
    boolean acceptAppointment(Long id);
    boolean declineAppointment(Long id, String reason);
    boolean finishAppointment(Long id);
    Collection<Appointments> findAll();
    Collection<Appointments> findAllPendingForProvider(Long serviceProviderId) throws NonexistingEntityException;
    Collection<Appointments> findAllAcceptedForProvider(Long serviceProviderId) throws NonexistingEntityException;
    Collection<Appointments> findAllDeclinedForProvider(Long serviceProviderId) throws NonexistingEntityException;
    Collection<Appointments> findAllFinishedForProvider(Long serviceProviderId) throws NonexistingEntityException;
    Collection<Appointments> findAllPendingFromUser(Long userId);
    Collection<Appointments> findAllAcceptedFromUser(Long userId);
    Collection<Appointments> findAllDeclinedFromUser(Long userId);
    Collection<Appointments> findAllFinishedFromUser(Long userId);
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
