package com.jvyacheslav.messenger.controllers;


import com.jvyacheslav.messenger.database.ChatDatabaseManager;
import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.Chat;
import com.jvyacheslav.messenger.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;

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
        if(user.isAuth()) {
            model.addAttribute("users", getListOfUsers(user));
        }
        return "mainPage";
    }
    private List<User> getListOfUsers(User user){
        List<User> users = userDatabaseManager.findAll();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(user.getUsername())){
                users.remove(i);
            }
        }
        for (int i = 0; i < users.size(); i++) {
            String usersId = "";
            List<String> arr = Arrays.asList(user.getEmail(), users.get(i).getEmail());
            Collections.sort(arr);
            for (int j = 0; j < arr.size(); j++) {
                usersId+= arr.get(j);
                if(j != arr.size()-1){
                    usersId+="-";
                }
            }
            Chat chat = chatDatabaseManager.getChatByUsersId(usersId);
            if(chat==null){
                chat = new Chat();
                chat.setUsersId(usersId);
                chatDatabaseManager.save(chat);
            }
            users.get(i).setUsersId(usersId);
        }
        return users;
    }
}