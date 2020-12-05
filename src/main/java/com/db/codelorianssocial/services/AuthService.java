package com.db.codelorianssocial.services;

import com.db.codelorianssocial.entity.RequestUser;
import com.db.codelorianssocial.entity.User;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users = new HashMap<>();
    private static AuthService instance = null;

    private AuthService(){}

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

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

    public boolean login(RequestUser user) {
        System.out.println("Intru in user");
        System.out.println(user);
        if (users.containsKey(user.getId()) && users.get(user.getId()).getPassword().equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    public void showUsers() {
        System.out.println(users);
    }
}
