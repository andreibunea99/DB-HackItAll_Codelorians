package com.db.codelorianssocial.utils;

import com.db.codelorianssocial.models.CurrentUser;

import java.sql.Time;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Stats {
    public static void showStats(CurrentUser currUser) {
        for (Map.Entry<String, Long> entry : currUser.getTimeInRooms().entrySet()) {
            long convert = TimeUnit.SECONDS.convert(entry.getValue(), TimeUnit.NANOSECONDS);
            System.out.println("Time spent in room " + entry.getKey() + " = " + convert + "s.");
        }
    }
}
