package com.db.codelorianssocial.utils;

import com.db.codelorianssocial.models.CurrentUser;

import java.util.Map;

public class Stats {
    public static void showStats(CurrentUser currUser) {
        for (Map.Entry<String, Integer> entry : currUser.getTimeInRooms().entrySet()) {
            System.out.println("Time spent in room " + entry.getKey() + "= " + entry.getValue());
        }
    }
}
