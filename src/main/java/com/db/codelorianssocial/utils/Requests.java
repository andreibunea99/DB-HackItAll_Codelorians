package com.db.codelorianssocial.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Requests {
    public static String sendMessage(String URL, String message) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String getCommandContent(int command, String user) {
        String ret = "{\"content\" : \"-";

        switch (command) {
            case 101: {
                ret += "mute ";
                break;
            }
            case 102: {
                ret += "unmute ";
                break;
            }
            case 103: {
                ret += "deafen ";
                break;
            }
            case 104: {
                ret += "undeafen ";
                break;
            }
            case 105: {
                ret += "exit ";
                break;
            }
        }

        ret += user + "\"}";

        return ret;
    }
}
