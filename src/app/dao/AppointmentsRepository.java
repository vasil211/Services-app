package app.dao;

import app.model.Appointments;

import java.util.Collection;

public interface AppointmentsRepository extends Repository<Long, Appointments> {
    boolean acceptAppointment(Long id);
    boolean declineAppointment(Long id);
    boolean finishAppointment(Long id);
    Long countFinishedForProvider(Long serviceProviderId);
    Collection<Appointments> findAllPending(Long serviceProviderId);
    Collection<Appointments> findAllAccepted(Long serviceProviderId);
    Collection<Appointments> findAllDeclined(Long serviceProviderId);
    Collection<Appointments> findAllFinished(Long serviceProviderId);
}


