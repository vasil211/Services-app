package app.model;

import app.dao.Identifiable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class Appointments implements Identifiable<Long> {
    private Long id;
    private Long serviceProviderId;
    private Long userId;
    private Long postId;
    private String state;
    private String address;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Appointments() {
    }

    public Appointments(Long id, Long serviceProviderId, Long userId, Long postId, String state, String address, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.serviceProviderId = serviceProviderId;
        this.userId = userId;
        this.postId = postId;
        this.state = state;
        this.address = address;
        this.created = created;
        this.updated = updated;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public String toString() {
        return new StringJoiner(", ",  "Finished jobs: \n", "")
                .add("address: '" + address + "'")
                .add("created: " + created.format(formatter))
                .add("finished: " + updated.format(formatter))
                .toString();
    }
}
