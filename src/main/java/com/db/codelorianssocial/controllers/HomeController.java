package com.db.codelorianssocial.controllers;

import com.db.codelorianssocial.dao.*;
import com.db.codelorianssocial.entity.Message;
import com.db.codelorianssocial.entity.RequestUser;
import com.db.codelorianssocial.entity.User;
import com.db.codelorianssocial.models.CurrentUser;
import com.db.codelorianssocial.services.AuthService;
import com.db.codelorianssocial.models.Room;
import com.db.codelorianssocial.services.ChatService;
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
    private AuthService authService = AuthService.getInstance();
    String loginFailed = null;
    String registerFailed = null;
    String registerSuccess = null;
    boolean isLogged = false;
    boolean triedButton = false;
    String accessFailed = null;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Constants.dbDriver);
        dataSource.setUrl(Constants.dbURL);
        dataSource.setUsername(Constants.dbUser);
        dataSource.setPassword(Constants.dbPassword);

        return dataSource;
    }

    @Bean
    public UserDao getUserDAO(DataSource dataSource) {
        return new UserDaoImpl(dataSource);
    }

    @Bean
    public ChatDao getchatDAO(DataSource dataSource) {
        return new ChatDaoImpl(dataSource);
    }
    @Bean
    public SecondChatDao getsecondchatDAO(DataSource dataSource) {
        return new SecondChatDaoImpl(dataSource);
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private SecondChatDao secondChatDao;

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

        triedButton = true;

        if (!isLogged) {
            accessFailed = "Please login!";
            return mainPage(model);
        } else {
            accessFailed = null;
        }

        ArrayList<String> participants = roomsService.getParticipants();

        model.addAttribute("rooms", participants);

        if (request.getParameter("id") == null) {
            return "discord";
        }
        int roomId = Integer.parseInt(request.getParameter("id"));

        if (roomId > 100) {
            Requests.sendMessage(Constants.PostURL, Requests.getCommandContent(roomId, myUser.getName()));
            if (roomId == 105) {
                long roomExitTime = System.nanoTime();
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

        if (request.getParameter("id").equals("5")) {
            return getGame2(model);
        }

        return "discord";
    }

    @RequestMapping("/gameroom1")
    public String getGame1(Model model) {
        chatDao.save(new Message(AuthService.getUser().getId(), "entered the chat"));
        List<String> userList = ChatService.getInstance().getUsersList(chatDao);

//        ArrayList<String> peopleInGameRoom = roomsService.getRooms().get(3).getPeopleInRoom();
//        for (int i = 0; i < peopleInGameRoom.size(); ++i) {
//            userList.add(peopleInGameRoom.get(i));
//        }

        List<String> messageList = ChatService.getInstance().getMessageList(chatDao);
        model.addAttribute("list", userList);
        model.addAttribute("messages", messageList);
        return "game1";
    }

    @RequestMapping(value="/chat", method = RequestMethod.POST)
    public String chat(@ModelAttribute("message")String message, Model model) {
        if (message != null) {
            chatDao.save(new Message(AuthService.getUser().getId(), message));
        }

        return getGame1(model);
    }

    @RequestMapping("/gameroom2")
    public String getGame2(Model model) {
        secondChatDao.save(new Message(AuthService.getUser().getId(), "entered the chat"));
        List<String> userList = ChatService.getInstance().getUsersList(secondChatDao);

//        ArrayList<String> peopleInGameRoom = roomsService.getRooms().get(3).getPeopleInRoom();
//        for (int i = 0; i < peopleInGameRoom.size(); ++i) {
//            userList.add(peopleInGameRoom.get(i));
//        }

        List<String> messageList = ChatService.getInstance().getMessageList(secondChatDao);
        model.addAttribute("list", userList);
        model.addAttribute("messages", messageList);
        return "game2";
    }

    @RequestMapping(value="/chat2", method = RequestMethod.POST)
    public String chat2(@ModelAttribute("message")String message, Model model) {
        secondChatDao.save(new Message(AuthService.getUser().getId(), message));

        return getGame2(model);
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

        // Rooms to html
        ArrayList<String> participants = roomsService.getParticipants();

        model.addAttribute("rooms", participants);

        return getMap(model);
    }

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
