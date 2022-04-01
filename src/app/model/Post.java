package app.model;

import app.dao.Identifiable;

import java.time.LocalDateTime;

public class Post implements Identifiable<Long> {
    private Long id;
    private User user;
    private Category category;
    private String name;
    private String info;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String DeletedReason;

    public Post() {
    }

    public Post(Long id, User user, Category category, String name, String info, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.name = name;
        this.info = info;
        this.created = created;
        this.modified = modified;
    }

    public String getDeletedReason() {
        return DeletedReason;
    }

    public void setDeletedReason(String deletedReason) {
        DeletedReason = deletedReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
}
