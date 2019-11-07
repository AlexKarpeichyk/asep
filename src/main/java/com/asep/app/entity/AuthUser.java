package com.asep.app.entity;

public class AuthUser {
    private String name;
    private String password;

    public AuthUser() {

    }

    public AuthUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String email) {
        this.name = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}