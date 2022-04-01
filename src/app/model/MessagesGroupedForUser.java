package app.model;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class MessagesGroupedForUser {
    private Long id;
    private String firstName;
    private String LastName;
    private LocalDateTime sent;
    private Long userId;
    private Long service_providerId;
    private String name;
    private Long count;
    public MessagesGroupedForUser() {
    }

    public MessagesGroupedForUser(Long id, String firstName, String lastName, LocalDateTime sent, Long userId, Long service_providerId, String name) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.sent = sent;
        this.userId = userId;
        this.service_providerId = service_providerId;
        this.name = name;
    }
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", MessagesGroupedForUser.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("LastName='" + LastName + "'")
                .add("sent=" + sent)
                .add("userId=" + userId)
                .add("service_providerId=" + service_providerId)
                .add("name='" + name + "'")
                .add("count=" + count)
                .toString();
    }
}
