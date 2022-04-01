package app.model;

import app.dao.Identifiable;

import java.time.LocalDateTime;

public class Rating implements Identifiable<Long> {
    private Long id;
    private User userProvider;
    private User user;
    private Post post;
    private float rating;
    private String comment;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String DeletedReason;

    public Rating() {
    }

    public Rating(Long id, User userProvider, User user, Post post, short rating, String comment, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.userProvider = userProvider;
        this.user = user;
        this.post = post;
        this.rating = rating;
        this.comment = comment;
        this.created = created;
        this.modified = modified;
    }

    public String getDeletedReason() {
        return DeletedReason;
    }

    public void setDeletedReason(String deletedReason) {
        DeletedReason = deletedReason;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserProvider() {
        return userProvider;
    }

    public void setUserProvider(User userProvider) {
        this.userProvider = userProvider;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
