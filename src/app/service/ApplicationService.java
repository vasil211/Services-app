package app.service;

import app.model.Application;
import app.model.User;

import java.util.Collection;

public interface ApplicationService{
    Collection<Application> getAllApplications();
    Collection<Application> getAllRejectedApplications();
    void acceptApplication(Long id);
    void declineApplication(Long id, String reason);
    void createApplication(Application application);
    Application getLastApplicationForUser(User user);

}
