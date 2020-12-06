package com.db.codelorianssocial.dao;

import com.db.codelorianssocial.entity.User;

import java.util.List;

public interface UserDao {
    public int save(User user);

    public int update(User user);

    public User get(Integer id);

    public User get(String username);

    public int delete(Integer id);

    public List<User> list();
}
