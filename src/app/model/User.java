package app.model;

import app.dao.Identifiable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class User implements Identifiable<Long> {
    private Long id;
    private Role role;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean isOnline;
    private LocalDateTime lastOnline;
    private LocalDateTime modified;
    private LocalDateTime created;


    public User() {
    }

    public User(Long id, Role role, String userName, String password, String email, String firstName, String lastName, String phone, LocalDateTime lastOnline, LocalDateTime modified, LocalDateTime created) {
        this.id = id;
        this.role = role;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.lastOnline = lastOnline;
        this.modified = modified;
        this.created = created;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(LocalDateTime lastOnline) {
        this.lastOnline = lastOnline;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "Users: ", " \n");
        sj.add("\n" + getId().toString())
                .add(getRole().toString())
                .add(getUserName())
                .add(getEmail())
                .add(getFirstName())
                .add(getLastName())
                .add(getPhone())
                .add(getIsOnline() ? "Online" : "Offline")
                .add("Last online" + getLastOnline().format(formatter))
                .add("Modified: " + getModified().format(formatter))
                .add("Created: " + getCreated().format(formatter));
        return sj.toString();
    }
}
