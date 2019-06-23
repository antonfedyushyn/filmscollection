package ua.com.google.fediushyn.anton.model;

public enum UserRole {
    ADMIN, USER, ANONYMOUS;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
