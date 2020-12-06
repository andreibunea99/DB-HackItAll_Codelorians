package com.db.codelorianssocial.services;

import com.db.codelorianssocial.dao.UserDao;
import com.db.codelorianssocial.dao.UserDaoImpl;
import com.db.codelorianssocial.entity.RequestUser;
import com.db.codelorianssocial.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users = new HashMap<>();
    private static AuthService instance = null;

    private static User user = null;

    public void setUser(User user) {
        this.user = user;
    }

    public static User getUser() {
        return user;
    }

    private AuthService(){}

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean register(User user, UserDao userDao) {
        if (userDao.get(user.getId()) != null) {
            return false;
        }
        else {
            userDao.save(user);
            return true;
        }
    }

    public boolean login(RequestUser user, UserDao userDao) {
        if (userDao.get(user.getId()) == null) {
            return false;
        } else if (!userDao.get(user.getId()).getPassword().equals(user.getPassword())) {
            return false;
        }
        else {
            setUser(userDao.get(user.getId()));
            return true;
        }
    }

    public void showUsers() {
        System.out.println(users);
    }
}
