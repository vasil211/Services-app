package app.dao;

import app.model.Appointments;

import java.util.Collection;

public interface AppointmentsRepository extends Repository<Long, Appointments> {
    boolean acceptAppointment(Long id);
    boolean declineAppointment(Long id, String reason);
    boolean finishAppointment(Long id);
    Long countFinishedForProvider(Long serviceProviderId);
    Collection<Appointments> findAllPendingForUser(Long serviceProviderId);
    Collection<Appointments> findAllAcceptedForUser(Long serviceProviderId);
    Collection<Appointments> findAllDeclinedForUser(Long serviceProviderId);
    Collection<Appointments> findAllFinishedForUser(Long serviceProviderId);
    Collection<Appointments> findAllPending();
    Collection<Appointments> findAllAccepted();
    Collection<Appointments> findAllDeclined();
    Collection<Appointments> findAllFinished();

    Collection<Appointments> findAllPendingFromUser(Long userId);

    Collection<Appointments> findAllAcceptedFromUser(Long userId);

    Collection<Appointments> findAllDeclinedFromUser(Long userId);

    Collection<Appointments> findAllFinishedFromUser(Long userId);
}


