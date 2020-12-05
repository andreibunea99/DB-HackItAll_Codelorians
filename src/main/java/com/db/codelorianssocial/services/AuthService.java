package com.db.codelorianssocial.services;

import com.db.codelorianssocial.entity.User;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users = new HashMap<>();

    public boolean register(User user) {
        if (users.containsKey(user.getId())) {
            System.out.println("User already has an account");
            return false;
        }
        else {
            users.put(user.getId(), user);
            System.out.println("Registered!");
            return true;
        }
    }

    public boolean login(String username, String password) {
        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
