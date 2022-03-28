package app.service;

import app.exeption.InvalidEntityDataException;
import app.model.Appointments;
import app.model.Post;

import java.util.Collection;

public interface AppointmentsService {
    boolean acceptAppointment(Long id);
    boolean declineAppointment(Long id);
    boolean finishAppointment(Long id);
    Collection<Appointments> findAll();
    Collection<Appointments> findAllPending(Long serviceProviderId);
    Collection<Appointments> findAllAccepted(Long serviceProviderId);
    Collection<Appointments> findAllDeclined(Long serviceProviderId);
    Collection<Appointments> findAllFinished(Long serviceProviderId);
    Appointments findById(Long id) throws InvalidEntityDataException;
    Appointments create(Appointments appointments) throws InvalidEntityDataException;
    boolean delete(Long id);
    Long count();
    Long countFinishedForProvider(Long id);

}
