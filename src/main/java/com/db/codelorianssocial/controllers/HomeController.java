package com.db.codelorianssocial.controllers;

import com.db.codelorianssocial.services.RoomsService;
import com.db.codelorianssocial.utils.Constants;
import com.db.codelorianssocial.utils.Requests;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    boolean hidden = true;

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

        System.out.println(Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-salut\"}"));


        ArrayList<Integer> rooms = new ArrayList<Integer>();

        rooms.add(0);

        for (int i = 0; i < roomsService.getRooms().size(); ++i) {
            rooms.add(roomsService.getRooms().get(i).getId());
        }

        rooms.add(0);

        model.addAttribute("rooms", rooms);

        int roomId = Integer.parseInt(request.getParameter("id"));
        System.out.println(Requests.sendMessage(Constants.PostURL, "{\"content\" : \"" + roomId + "\"}"));

        return mainPage(model);
    }

    @GetMapping("/authentification")
    public String auth(Model model) {

        return "authentification";
    }
}
