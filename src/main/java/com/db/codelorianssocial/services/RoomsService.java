package com.db.codelorianssocial.services;
import com.db.codelorianssocial.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomsService {
    private List<Room> rooms = new ArrayList<>();

    public RoomsService() {
        add(0);
        add(1);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void add(int id) {
        rooms.add(new Room(id));
    }
}
