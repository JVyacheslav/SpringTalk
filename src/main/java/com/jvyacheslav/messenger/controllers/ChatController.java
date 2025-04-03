package com.jvyacheslav.messenger.controllers;

import com.jvyacheslav.messenger.database.ChatDatabaseManager;
import com.jvyacheslav.messenger.database.MessageDatabaseManager;
import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.Chat;
import com.jvyacheslav.messenger.dto.InputMessage;
import com.jvyacheslav.messenger.dto.Message;
import com.jvyacheslav.messenger.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@SessionAttributes(value = "user")
@RequestMapping("/chatPage")
@Controller
public class ChatController {
    @Autowired
    private MessageDatabaseManager messageDatabaseManager;
    @Autowired
    private ChatDatabaseManager chatDatabaseManager;
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private User user;

    @GetMapping
    @RequestMapping("/{usersId}")
    public String setForm(User user, Model model, @PathVariable String usersId){
        System.out.println(usersId);
        String[] arr = usersId.split("-");
        if(user.isAuth() && (arr[0].equals(user.getEmail()) || arr[1].equals(user.getEmail()))) {
            model.addAttribute("messages", messageDatabaseManager.findAllByUsersId(usersId));
            this.user = user;
            return "chatPage";
        } else{
            return "redirect:/";
        }
    }

    @MessageMapping("/chatPage/{usersId}")
    public void updateChat(@Payload InputMessage message, @DestinationVariable String usersId) {
        String[] arr = usersId.split("-");
        if(user.isAuth() && (arr[0].equals(user.getEmail()) || arr[1].equals(user.getEmail()))) {
            Message newMessage = new Message();
            newMessage.setUsersId(usersId);
            newMessage.setUsername(user.getUsername());
            newMessage.setText(prepareTextForSaving(message.getText()));
            messageDatabaseManager.saveMessage(newMessage);
            System.out.println(newMessage.getUsersId());
            this.simpMessagingTemplate.convertAndSend("/chatPage/"+usersId, newMessage);
        }
    }
    private String prepareTextForSaving(String message){
        message = message.replaceAll("\\\\", "");
        return message;
    }

}