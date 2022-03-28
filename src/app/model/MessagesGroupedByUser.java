package app.model;

import java.time.LocalDateTime;

public class MessagesGroupedByUser {
    private Long id;
    private String firstName;
    private String LastName;
    private LocalDateTime sent;
    private Long userId;
    private Long service_providerId;
    private String name;

    public MessagesGroupedByUser() {
    }

    public MessagesGroupedByUser(Long id, String firstName, String lastName, LocalDateTime sent, Long userId, Long service_providerId, String name) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.sent = sent;
        this.userId = userId;
        this.service_providerId = service_providerId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public LocalDateTime getSent() {
        return sent;
    }

    public void setSent(LocalDateTime sent) {
        this.sent = sent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getService_providerId() {
        return service_providerId;
    }

    public void setService_providerId(Long service_providerId) {
        this.service_providerId = service_providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
