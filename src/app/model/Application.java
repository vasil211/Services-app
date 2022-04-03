package app.model;

import app.dao.Identifiable;

import java.time.LocalDateTime;

public class Application implements Identifiable<Long> {
    private Long id;
    private User user;
    private String reason;
    private LocalDateTime created;
    private String status;
    private String reasonForRejection;
    private LocalDateTime rejected;

    public Application() {

    }
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReasonForRejection() {
        return reasonForRejection;
    }

    public void setReasonForRejection(String reasonForRejection) {
        this.reasonForRejection = reasonForRejection;
    }

    public LocalDateTime getRejected() {
        return rejected;
    }

    public void setRejected(LocalDateTime rejected) {
        this.rejected = rejected;
    }
}