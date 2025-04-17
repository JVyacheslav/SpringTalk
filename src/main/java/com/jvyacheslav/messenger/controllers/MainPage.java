package com.jvyacheslav.messenger.controllers;


import com.jvyacheslav.messenger.database.ChatDatabaseManager;
import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.Chat;
import com.jvyacheslav.messenger.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;
//Main Page class
@Controller
@RequestMapping("/")
@SessionAttributes(value = "user")
public class MainPage {
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @Autowired
    private ChatDatabaseManager chatDatabaseManager;
    @GetMapping
    public String setForm(User user, Model model){
        if(chatDatabaseManager.getChatByUsersId("generalChat")==null) {
            //adds a general chat to the database if it is missing for some reason
            chatDatabaseManager.save(new Chat("generalChat"));
        }
        //creates two users for testing without two-factor authentication
        User user1 = new User();
        user1.setEmail("java.jamtest@gmail.com");
        user1.setPass("1234567");
        user1.setUsername("user1");
        User user2 = new User();
        user2.setEmail("vyacheslav.java@gmail.com");
        user2.setUsername("user2");
        user2.setPass("12345678");
        if(userDatabaseManager.findByUsername("user1") == null) {
            userDatabaseManager.saveUser(user1);
        }
        if(userDatabaseManager.findByUsername("user2")==null) {
            userDatabaseManager.saveUser(user2);
        }
        if(user.isAuth()) {
            //if the user is authenticated, he will be shown a list of all chats
            model.addAttribute("generalChat", "/chatPage/generalChat");
            model.addAttribute("users", getListOfUsers(user));
        }
        return "mainPage";
    }
    private List<User> getListOfUsers(User user){
        List<User> users = userDatabaseManager.findAll();
        for (int i = 0; i < users.size(); i++) {
            //removes the user from the list of possible chats
            if(users.get(i).getUsername().equals(user.getUsername())){
                users.remove(i);
            }
        }
        for (int i = 0; i < users.size(); i++) {
            //create chat id based on user ids
            String usersId = "";
            List<String> arr = Arrays.asList(String.valueOf(user.getId()),String.valueOf(users.get(i).getId()));
            Collections.sort(arr);
            for (int j = 0; j < arr.size(); j++) {
                usersId+= arr.get(j);
                if(j != arr.size()-1){
                    usersId+="-";
                }
            }
            Chat chat = chatDatabaseManager.getChatByUsersId(usersId);
            //checks for the presence of a chat in the database
            if(chat==null){
                chat = new Chat();
                chat.setUsersId(usersId);
                //saves if chat does not exist
                chatDatabaseManager.save(chat);
            }
            users.get(i).setUsersId(usersId);
        }
        return users;
    }
    @PostMapping
    public String logOut(User user){
        user.clearUser();
        return "mainPage";
    }
}