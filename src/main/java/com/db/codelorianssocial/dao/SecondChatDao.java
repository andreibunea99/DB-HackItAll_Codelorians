package com.db.codelorianssocial.dao;

import com.db.codelorianssocial.entity.Message;

import java.util.List;

public interface SecondChatDao {
    public int save(Message message);

    public List<Message> list();
}
