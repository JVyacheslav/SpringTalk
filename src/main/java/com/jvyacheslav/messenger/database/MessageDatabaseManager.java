package com.jvyacheslav.messenger.database;

import com.jvyacheslav.messenger.dto.Message;
import com.jvyacheslav.messenger.interfaces.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageDatabaseManager {
    @Autowired
    private MessageRepository messageRepository;
    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }
    public List<Message> findAll(){
        return messageRepository.findAll();
    }
    public List<Message> findAllByUsersId(String usersId){
        return messageRepository.findAllByUsersId(usersId);
    }
}
