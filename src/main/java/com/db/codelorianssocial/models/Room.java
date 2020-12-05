package com.db.codelorianssocial.models;

import java.util.ArrayList;

public class Room {
    private int id;
    private ArrayList<String> peopleInRoom = new ArrayList<String>();

    public Room(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void removeFromRoom(String person) {
        peopleInRoom.remove(person);
    }

    public void addToRoom(String person) {
        peopleInRoom.add(person);
    }

    public ArrayList<String> getPeopleInRoom() {
        return peopleInRoom;
    }
}
