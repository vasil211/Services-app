package app.dao;

import app.model.Application;
import app.model.User;

import java.util.Collection;

public interface ApplicationRepository extends Repository<Long, Application> {


    void acceptApplication(Long id);

    void declineApplication(Long id, String reason);

    Application getLastApplicationForUser(User user);

    Collection<Application> findAllRejected();
}
