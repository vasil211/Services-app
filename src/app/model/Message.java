package app.model;

import app.dao.Identifiable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class Message implements Identifiable<Long> {
    private Long id;
    private Long sender;
    private User userProvider;
    private User user;
    private Post post;
    private String message;
    private LocalDateTime sent;


    public Message() {
    }


    public Message(Long sender, User userProvider, User user, Post post, String message, LocalDateTime sent) {
        this.sender = sender;
        this.userProvider = userProvider;
        this.user = user;
        this.post = post;
        this.message = message;
        this.sent = sent;
    }



    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public LocalDateTime getSent() {
        return sent;
    }

    public void setSent(LocalDateTime sent) {
        this.sent = sent;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String toString() {
        return new StringJoiner(" ", "\n", "")
                .add(message)
                .add("\n" + sent.format(formatter))
                .toString();
    }
}
