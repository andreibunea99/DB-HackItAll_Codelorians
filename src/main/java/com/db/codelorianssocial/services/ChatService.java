package com.db.codelorianssocial.services;

import com.db.codelorianssocial.dao.UserDao;
import com.db.codelorianssocial.entity.Message;
import com.db.codelorianssocial.entity.RequestUser;
import com.db.codelorianssocial.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {
    private List<Message> list = new ArrayList<>();
    private static ChatService instance = null;

    private ChatService(){}

    public static ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }


    public List<String> getMessageList() {
        List<String> stringList = new ArrayList<>();
        for (Message msg : list) {
            String message = "[" + msg.getUsername() + "]" + " " + msg.getMessage();
            stringList.add(message);
        }

        return stringList;
    }
}
