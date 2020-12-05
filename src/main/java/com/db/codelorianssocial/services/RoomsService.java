package com.db.codelorianssocial.services;
import com.db.codelorianssocial.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomsService {
    private ArrayList<Room> rooms = new ArrayList<>();

    public RoomsService() {
        rooms.add(new Room(1));
        rooms.add(new Room(2));
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void add(int id) {
        rooms.add(new Room(id));
    }
}
