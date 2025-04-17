package com.jvyacheslav.messenger.controllers.chat;

import com.jvyacheslav.messenger.database.MessageDatabaseManager;
import com.jvyacheslav.messenger.dto.InputMessage;
import com.jvyacheslav.messenger.dto.Message;
import com.jvyacheslav.messenger.dto.User;
import com.jvyacheslav.messenger.service.images.Base64Converter;
import com.jvyacheslav.messenger.service.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@SessionAttributes(value = "user")
@RequestMapping("/chatPage")
@Controller
public class ChatController {
    @Autowired
    private MessageDatabaseManager messageDatabaseManager;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private Base64Converter base64Converter;
    @Autowired
    private MessageManager messageManager;
    private User user;
    @GetMapping("/generalChat")
    public String setForm(User user, Model model) throws IOException {
        if(user.isAuth()){
            //displays all messages from the database
            List<Message> messages = messageDatabaseManager.findAllByUsersId("generalChat");
            for(Message message : messages){
                if(message.isAttachment()){
                    message.setImg(base64Converter.getImageAsBase64String(message.getMessageId()));
                }
            }
            model.addAttribute("messages", messages);
            this.user = user;
            return "chatPage";
        } else{
            return "redirect:/";
        }

    }

    @MessageMapping("/chatPage/generalChat")
    public void updateChat(@Payload InputMessage message) throws IOException {
        if(user.isAuth()){
            //checks the message type of the incoming message
            if (message.getType().equals("save")  && (!message.getContent().equals("") || message.getAttachment() != null)){
                Message outputMessage = messageManager.sendMessage(message, user.getUsername(), "generalChat");
                this.simpMessagingTemplate.convertAndSend("/chatPage/generalChat", outputMessage);
            } else if (message.getType().equals("delete")){
                messageDatabaseManager.deleteById(Integer.valueOf(message.getContent()));
                this.simpMessagingTemplate.convertAndSend("/chatPage/generalChat", message);
            }
        }
    }
    @GetMapping
    @RequestMapping("/{usersId}")
    public String setForm(User user, Model model, @PathVariable String usersId) throws IOException {
        //checks if the participant is a member of the chat
        if(usersId.contains("-")) {
            System.out.println(usersId);
            String[] arr = usersId.split("-");
            if (user.isAuth() && (arr[0].equals(String.valueOf(user.getId())) || arr[1].equals(String.valueOf(user.getId())))) {
                //displays all messages from the database
                List<Message> messages = messageDatabaseManager.findAllByUsersId(usersId);
                for (Message message : messages) {
                    if (message.isAttachment()) {
                        message.setImg(base64Converter.getImageAsBase64String(message.getMessageId()));
                    }
                }
                model.addAttribute("messages", messages);
                this.user = user;
                return "chatPage";
            }
        }
        return "redirect:/";

    }

    @MessageMapping("/chatPage/{usersId}")
    public void updateChat(@Payload InputMessage message, @DestinationVariable String usersId) throws IOException {
        if(usersId.contains("-")) {
            String[] arr = usersId.split("-");
            //checks if the participant is a member of the chat

            if (user.isAuth() && (arr[0].equals(String.valueOf(user.getId())) || arr[1].equals(String.valueOf(user.getId())))) {

                //checks the message type of the incoming message
                if (message.getType().equals("save") && (message.getContent() != null || message.getAttachment() != null)) {
                    Message outputMessage = messageManager.sendMessage(message, user.getUsername(), usersId);
                    this.simpMessagingTemplate.convertAndSend("/chatPage/" + usersId, outputMessage);
                } else if (message.getType().equals("delete")) {
                    messageDatabaseManager.deleteById(Integer.valueOf(message.getContent()));
                    this.simpMessagingTemplate.convertAndSend("/chatPage/" + usersId, message);
                }
            }
        }
    }

}