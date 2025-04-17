package com.jvyacheslav.messenger.service;

import com.jvyacheslav.messenger.database.MessageDatabaseManager;
import com.jvyacheslav.messenger.dto.InputMessage;
import com.jvyacheslav.messenger.dto.Message;
import com.jvyacheslav.messenger.service.images.Base64Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class MessageManager {
    @Autowired
    private Base64Converter base64Converter;
    @Autowired
    private MessageDatabaseManager messageDatabaseManager;
    public Message sendMessage(InputMessage clientMessage, String username, String usersId) throws IOException {
        //Creates and saves a message to the database
        Message newMessage = new Message();
        newMessage.setUsersId(usersId);
        newMessage.setUsername(username);
        newMessage.setText(prepareTextForSaving(clientMessage.getContent()));
        newMessage.setAttachment(clientMessage.getAttachment() != null);
        newMessage = messageDatabaseManager.saveMessage(newMessage);
        if (newMessage.isAttachment()) {
            base64Converter.saveBase64StringAsImage(clientMessage.getAttachment(), newMessage.getMessageId());
            newMessage.setImg(base64Converter.getImageAsBase64String(newMessage.getMessageId()));
        }
        return newMessage;
    }
    private String prepareTextForSaving(String message){
        message = message.replaceAll("\\\\", "");
        return message;
    }
}
