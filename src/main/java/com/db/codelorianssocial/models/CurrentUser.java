package com.db.codelorianssocial.models;

import java.util.HashMap;
import java.util.Map;

public class CurrentUser {
    private String name;
    private String currentRoom;
    private Map<String, Long> timeInRooms = new HashMap<>();

    public CurrentUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Long> getTimeInRooms() {
        return timeInRooms;
    }

    public void setTimeInRooms(Map<String, Long> timeInRooms) {
        this.timeInRooms = timeInRooms;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void newTimeForRoom(String room, Long time) {
        timeInRooms.remove(room);
        timeInRooms.put(room, time);
    }
}
