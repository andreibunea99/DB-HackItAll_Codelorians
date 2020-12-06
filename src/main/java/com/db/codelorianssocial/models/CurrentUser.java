package com.db.codelorianssocial.models;

import java.util.HashMap;
import java.util.Map;

public class CurrentUser {
    private String name;
    private Map<String, Integer> timeInRooms = new HashMap<>();

    public CurrentUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getTimeInRooms() {
        return timeInRooms;
    }

    public void setTimeInRooms(Map<String, Integer> timeInRooms) {
        this.timeInRooms = timeInRooms;
    }
}
