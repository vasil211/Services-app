package app.model;

public enum Role {
    USER(1), SERVICE_PROVIDER(2), MODERATOR(3), ADMIN(4);

    Role(int role) {
        this.role = role;
    }
    private int role;

    public int getRole() {
        return role;
    }
}
