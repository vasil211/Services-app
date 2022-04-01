package app.model;

import app.dao.Identifiable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class Appointments implements Identifiable<Long> {
    private Long id;
    private User serviceProvider;
    private User user;
    private Post post;
    private String state;
    private String address;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String declineComment;
    public Appointments() {
    }

    public Appointments(Long id, User serviceProvider, User user, Post post, String state, String address,
                        LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.serviceProvider = serviceProvider;
        this.user = user;
        this.post = post;
        this.state = state;
        this.address = address;
        this.created = created;
        this.updated = updated;
    }

    public String getDeclineComment() {
        return declineComment;
    }

    public void setDeclineComment(String declineComment) {
        this.declineComment = declineComment;
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

    public User getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(User serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
