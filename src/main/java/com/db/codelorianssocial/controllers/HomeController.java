package com.db.codelorianssocial.controllers;

import com.db.codelorianssocial.entity.RequestUser;
import com.db.codelorianssocial.entity.User;
import com.db.codelorianssocial.services.AuthService;
import com.db.codelorianssocial.models.Room;
import com.db.codelorianssocial.services.RoomsService;
import com.db.codelorianssocial.utils.Constants;
import com.db.codelorianssocial.utils.Requests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    boolean hidden = true;
    String myUser = "Cosmin";

    RoomsService roomsService = new RoomsService();

    public void writeInCommand() throws IOException {
        String str = "Hello";
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/Discord/command.txt"));
        writer.write(str);

        writer.close();
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        try {
            writeInCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("isHidden", hidden);
        return "index";
    }



    @GetMapping("/codelorians-discord")
    public String discordPage(Model model, HttpServletRequest request) throws IOException, InterruptedException {
        hidden = !hidden;

        try {
            System.out.println(Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-salut\"}"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ArrayList<Integer> rooms = new ArrayList<Integer>();

        rooms.add(0);

        for (int i = 0; i < roomsService.getRooms().size(); ++i) {
            rooms.add(roomsService.getRooms().get(i).getId());
        }

        rooms.add(0);

        model.addAttribute("rooms", rooms);

        if (request.getParameter("id") == null) {
            return mainPage(model);
        }
        int roomId = Integer.parseInt(request.getParameter("id"));

        String roomID = "";
        String userID = "";
        if (roomId == 1) {
            roomID = "784718151219937294";
        }

        if (roomId == 2) {
            roomID = "784720436473888789";
        }

        if (myUser.equals("Cosmin")) {
            userID = "203203790017527808";
        }

        System.out.println(Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-move " + userID + " " + roomID + "\"}"));

        return mainPage(model);
    }

    @GetMapping("/get-rooms-info")
    public String getRoomsInfo(Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String newRoom = request.getParameter("newChannel");
        String oldRoom = request.getParameter("oldChannel");

        int newRoomId = 0, oldRoomId = 0;
        if (newRoom.equals("784718151219937294")) {
            newRoomId = 1;
        }
        if (oldRoom.equals("784718151219937294")) {
            oldRoomId = 1;
        }
        if (newRoom.equals("784720436473888789")) {
            newRoomId = 2;
        }
        if (oldRoom.equals("784720436473888789")) {
            oldRoomId = 2;
        }

        ArrayList<Room> rooms = roomsService.getRooms();
        if (!newRoom.equals("null")) {
            rooms.get(newRoomId - 1).addToRoom(name);
        }
        if (!oldRoom.equals("null")) {
            rooms.get(oldRoomId - 1).removeFromRoom(name);
        }

        for (Room room : rooms) {
            System.out.println("People in room " + room.getId() + ": ");
            for (String person : room.getPeopleInRoom()) {
                System.out.print(person + " ");
            }
            System.out.println();
        }
        System.out.println();

//        System.out.println(name + " " + newRoom + " " + oldRoom);

        return mainPage(model);
    }

    private AuthService authService;

    @GetMapping("/auth-login")
    public String login(Model model) {
        RequestUser user = new RequestUser();
        model.addAttribute("user", user);
        return "authentification";
    }

    @PostMapping("/auth-login")
    public String login(@ModelAttribute RequestUser user, Model model) {
        model.addAttribute("user", user);
        System.out.println(user.getId() + " " + user.getPassword());
        return "authentification";
    }



    @GetMapping("/authentification")
    public String auth(Model model) {

        return "authentification";
    }
}
