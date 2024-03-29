package com.db.codelorianssocial.entity;

public class User {
    private String id;
    private String password;
    private String email;
    private String discord;

    public User(String id, String email, String discord, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.discord = discord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", discord='" + discord + '\'' +
                '}';
    }
}
