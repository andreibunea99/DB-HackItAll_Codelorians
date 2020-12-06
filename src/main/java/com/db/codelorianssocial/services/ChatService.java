package com.db.codelorianssocial.services;

import com.db.codelorianssocial.dao.ChatDao;
import com.db.codelorianssocial.dao.SecondChatDao;
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

    public List<String> getMessageList(ChatDao chatDao) {
        List<String> stringList = new ArrayList<>();
        list = chatDao.list();
        for (Message msg : list) {
            String message = "[" + msg.getUsername() + "]" + " " + msg.getMessage();
            stringList.add(message);
        }

        return stringList;
    }

    public List<String> getUsersList(ChatDao chatDao) {
        List<String> userList = new ArrayList<>();
        list = chatDao.list();
        for (Message msg : list) {
            if (!userList.contains(msg.getUsername())) {
                userList.add(msg.getUsername());
            }
        }

        return userList;
    }

    public List<String> getUsersList(SecondChatDao chatDao) {
        List<String> userList = new ArrayList<>();
        list = chatDao.list();
        for (Message msg : list) {
            if (!userList.contains(msg.getUsername())) {
                userList.add(msg.getUsername());
            }
        }

        return userList;
    }

    public List<String> getMessageList(SecondChatDao chatDao) {
        List<String> stringList = new ArrayList<>();
        list = chatDao.list();
        for (Message msg : list) {
            String message = "[" + msg.getUsername() + "]" + " " + msg.getMessage();
            stringList.add(message);
        }

        return stringList;
    }
}
