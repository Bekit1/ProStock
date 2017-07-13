package net.ukr.bekit.model;

public enum UserRole {
    ADMIN, USER;
    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
