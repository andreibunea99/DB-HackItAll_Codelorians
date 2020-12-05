package com.db.codelorianssocial.services;
import com.db.codelorianssocial.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomsService {
    private ArrayList<Room> rooms = new ArrayList<>();

    public RoomsService() {
        rooms.add(new Room(1));
        rooms.add(new Room(2));
        rooms.add(new Room(3));
        rooms.add(new Room(4));
        rooms.add(new Room(5));
        rooms.add(new Room(6));
        rooms.add(new Room(7));
        rooms.add(new Room(8));
        rooms.add(new Room(9));
        rooms.add(new Room(10));
        rooms.add(new Room(11));
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void add(int id) {
        rooms.add(new Room(id));
    }

    public int getUserRoom(String username) {
        for (int i = 0; i < rooms.size(); ++i) {
            for (String name : rooms.get(i).getPeopleInRoom()) {
                if (name.equals(username)) {
                    return rooms.get(i).getId();
                }
            }
        }
        return 0;
    }
}
