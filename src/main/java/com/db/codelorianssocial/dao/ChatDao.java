package com.db.codelorianssocial.dao;

import com.db.codelorianssocial.entity.Message;
import com.db.codelorianssocial.entity.User;

import java.util.List;

public interface ChatDao {
    public int save(Message message);

    public List<Message> list();
}
