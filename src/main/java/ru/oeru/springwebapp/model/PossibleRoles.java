package ru.oeru.springwebapp.model;

public enum PossibleRoles {
    ROLE_USER, ROLE_ADMIN;
    public static String getUserRole(){
        return ROLE_USER.name();
    }
    public static String getAdminRole() {
        return ROLE_ADMIN.name();
    }
}
