package com.db.codelorianssocial.utils;

public class Constants {
    public static final String PostURL = "https://discord.com/api/webhooks/784736848563929109/Rf_0IWE3V-GHKd7g9RNOaWC-Y0ZdjfoPgGiKjNYjUZCQQX718S22J7XuQf_a9HogqZLx";
    public static final String[] roomIds = {
            "",
            "784718151219937294",
            "784720436473888789",
            "784862672242999336",
            "784863000133632000",
            "784863132555542590",
            "784863411442810920",
            "784863454510055444",
            "784863532528173086",
            "784863599561801758",
            "784863672379899904",
            "784862825637085204"
    };
    public static final String dbDriver = "com.mysql.jdbc.Driver";
    public static final String dbURL = "jdbc:mysql://ur91o7rxcka2s44f:PUUY8X6HB4zW33repWr1@bv3jx1dksltsqbot8aim-mysql.services.clever-cloud.com:3306/bv3jx1dksltsqbot8aim";
    public static final String dbUser = "ur91o7rxcka2s44f";
    public static final String dbPassword = "PUUY8X6HB4zW33repWr1";

    public static int getIdOfRoom(String roomId) {
        for (int i = 1; i < roomIds.length; ++i) {
            if (roomIds[i].equals(roomId)) {
                return i;
            }
        }

        return 0;
    }
}
