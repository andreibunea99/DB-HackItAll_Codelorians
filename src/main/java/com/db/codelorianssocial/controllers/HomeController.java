package com.db.codelorianssocial.controllers;

import com.db.codelorianssocial.dao.UserDao;
import com.db.codelorianssocial.dao.UserDaoImpl;
import com.db.codelorianssocial.entity.RequestUser;
import com.db.codelorianssocial.entity.User;
import com.db.codelorianssocial.models.CurrentUser;
import com.db.codelorianssocial.services.AuthService;
import com.db.codelorianssocial.models.Room;
import com.db.codelorianssocial.services.RoomsService;
import com.db.codelorianssocial.utils.Constants;
import com.db.codelorianssocial.utils.Requests;
import com.db.codelorianssocial.utils.Stats;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    boolean hidden = true;
    CurrentUser myUser = new CurrentUser("Adi");
    String loginFailed = null;
    String registerFailed = null;
    String registerSuccess = null;
    boolean isLogged = false;
    boolean triedButton = false;
    String accessFailed = null;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://ur91o7rxcka2s44f:PUUY8X6HB4zW33repWr1@bv3jx1dksltsqbot8aim-mysql.services.clever-cloud.com:3306/bv3jx1dksltsqbot8aim");
        dataSource.setUsername("ur91o7rxcka2s44f");
        dataSource.setPassword("PUUY8X6HB4zW33repWr1");

        return dataSource;
    }

    @Bean
    public UserDao getUserDAO(DataSource dataSource) {
        return new UserDaoImpl(dataSource);
    }

    @Autowired
    private UserDao userDao;

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
        } else {
            accessFailed = null;
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

        if (roomId > 100) {
            if (roomId == 101) {
                Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-mute " + myUser.getName() + "\"}");
            }
            if (roomId == 102) {
                Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-unmute " + myUser.getName() + "\"}");
            }
            if (roomId == 103) {
                Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-deafen " + myUser.getName() + "\"}");
            }
            if (roomId == 104) {
                Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-undeafen " + myUser.getName() + "\"}");
            }
            if (roomId == 105) {
                Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-exit " + myUser.getName() + "\"}");

                Long roomExitTime = System.nanoTime();
                if (myUser.getCurrentRoom() != null) {
                    long lastTime = myUser.getTimeInRooms().get(myUser.getCurrentRoom());
                    myUser.newTimeForRoom(myUser.getCurrentRoom(), roomExitTime - lastTime);
                }

                Stats.showStats(myUser);
                myUser.getTimeInRooms().clear();
                myUser.setCurrentRoom(null);
                return "index";
            }
        } else {
            String roomID = Constants.roomIds[roomId];

            if (roomId != roomsService.getUserRoom(myUser.getName())) {
                Requests.sendMessage(Constants.PostURL, "{\"content\" : \"-move " + myUser.getName() + " " + roomID + "\"}");

                Long roomEnterTime = System.nanoTime();

                if (myUser.getCurrentRoom() != null) {
                    long lastTime = myUser.getTimeInRooms().get(myUser.getCurrentRoom());
                    myUser.newTimeForRoom(myUser.getCurrentRoom(), roomEnterTime - lastTime);
                }

                myUser.getTimeInRooms().put(String.valueOf(roomId), roomEnterTime);
                myUser.setCurrentRoom(String.valueOf(roomId));
            }
        }

        if (request.getParameter("id").equals("4")) {
            return getGame1(model);
        }

        return "discord";
    }

    @RequestMapping("/gameroom1")
    public String getGame1(Model model) {
        ArrayList<String> userList = new ArrayList<>();
        ArrayList<String> messageList = new ArrayList<>();

        ArrayList<String> peopleInGameRoom = roomsService.getRooms().get(3).getPeopleInRoom();
        for (int i = 0; i < peopleInGameRoom.size(); ++i) {
            userList.add(peopleInGameRoom.get(i));
        }

        model.addAttribute("list", userList);
        model.addAttribute("messages", messageList);
        return "game1";
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

//        for (Room room : rooms) {
//            System.out.println("People in room " + room.getId() + ": ");
//            for (String person : room.getPeopleInRoom()) {
//                System.out.print(person + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();

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
        if (!authService.login(user, userDao)) {
            loginFailed = "Userul nu exista!";
        } else {
            loginFailed = null;
            isLogged = true;
            accessFailed = null;
            page = "index";
        }

        myUser.setName(user.getId());

        model.addAttribute("loginFlag", loginFailed);

        return page;
    }

    public String getMap(Model model) {
        return "discord";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user")User user, Model model) {

        if (!authService.register(user, userDao)) {
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
