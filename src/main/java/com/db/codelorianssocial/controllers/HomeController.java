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
    String myUser = "Adi";
    String loginFailed = null;
    String registerFailed = null;
    String registerSuccess = null;
    boolean isLogged = false;
    boolean triedButton = false;
    String accessFailed = null;

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
        model.addAttribute("access", accessFailed);
        model.addAttribute("isHidden", hidden);
        return "index";
    }



    @GetMapping("/codelorians-discord")
    public String discordPage(Model model, HttpServletRequest request) throws IOException, InterruptedException {
        hidden = !hidden;

        ArrayList<String> participants = new ArrayList<String>();
        triedButton = true;

        if (!isLogged) {
            accessFailed = "Please login!";
            return mainPage(model);
        }

        participants.add("");

        for (int i = 0; i < roomsService.getRooms().size(); ++i) {
            StringBuilder ret = new StringBuilder();
            ArrayList<String> temp = roomsService.getRooms().get(i).getPeopleInRoom();
            for (String s : temp) {
                ret.append(s).append("   ");
            }
            participants.add(ret.toString());
        }

        participants.add("");

        model.addAttribute("rooms", participants);

        if (request.getParameter("id") == null) {
            return "discord";
        }
        int roomId = Integer.parseInt(request.getParameter("id"));

        String roomID = Constants.roomIds[roomId];

        if (roomId != roomsService.getUserRoom(myUser)) {
            Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-move " + myUser + " " + roomID + "\"}");
        }

        return "discord";
    }

    @GetMapping("/get-rooms-info")
    public String getRoomsInfo(Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String newRoom = request.getParameter("newChannel");
        String oldRoom = request.getParameter("oldChannel");

        int newRoomId, oldRoomId;

        oldRoomId = Constants.getIdOfRoom(oldRoom);
        newRoomId = Constants.getIdOfRoom(newRoom);

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

        // Rooms to html
        ArrayList<String> participants = new ArrayList<String>();

        participants.add("");

        for (int i = 0; i < roomsService.getRooms().size(); ++i) {
            String ret = "";
            ArrayList<String> temp = roomsService.getRooms().get(i).getPeopleInRoom();
            for (String s : temp) {
                ret += s + "   ";
            }
            participants.add(ret);
        }

        participants.add("");

        model.addAttribute("rooms", participants);

        return getMap(model);
    }

    private AuthService authService = AuthService.getInstance();

//    @RequestMapping(value="/authentification", method = RequestMethod.POST)
//    public String login(@ModelAttribute("user")RequestUser user, Model model) {
//        System.out.println(user.getId() + " " + user.getPassword());
//
//        return "discord";
//    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("user")RequestUser user, Model model) {
        String page = "authentification";

        System.out.println(user.getId() + " " + user.getPassword());
        if (!authService.login(user)) {
            loginFailed = "Userul nu exista!";
        } else {
            loginFailed = null;
            isLogged = true;
            page = "index";
        }

        myUser = user.getId();

        model.addAttribute("loginFlag", loginFailed);

        return page;
    }

    public String getMap(Model model) {
        return "discord";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user")User user, Model model) {

        if (!authService.register(user)) {
            registerFailed = "Userul deja exista!";
            registerSuccess = null;
        } else {
            registerFailed = null;
            registerSuccess = "Utilizatorul a fost creat cu success!";
        }

        authService.showUsers();

        model.addAttribute("registerFlag", registerFailed);
        model.addAttribute("registerFlagSuccess", registerSuccess);

        return "authentification";
    }



    @GetMapping("/authentification")
    public String auth(Model model) {

        return "authentification";
    }
}
