package app.service.impl;

import app.dao.ApplicationRepository;
import app.exeption.InvalidEntityDataException;
import app.model.Application;
import app.model.User;
import app.service.ApplicationService;
import app.service.validators.ApplicationValidation;

import java.util.Collection;

public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    @Override
    public Collection<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Collection<Application> getAllRejectedApplications() {
        return applicationRepository.findAllRejected();
    }

    @Override
    public void acceptApplication(Long id) {
        applicationRepository.acceptApplication(id);
    }

    @Override
    public void declineApplication(Long id, String reasonForDecline) {
        try {
            ApplicationValidation.validateDeclineReason(reasonForDecline);
            applicationRepository.declineApplication(id, reasonForDecline);
        }catch (InvalidEntityDataException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void createApplication(Application application) {
        try {
            ApplicationValidation.validateInformation(application.getReason());
            applicationRepository.create(application);
        }catch (InvalidEntityDataException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Application getLastApplicationForUser(User user) {
        return applicationRepository.getLastApplicationForUser(user);
    }

}
