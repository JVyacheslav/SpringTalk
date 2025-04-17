package com.jvyacheslav.messenger.database;

import com.jvyacheslav.messenger.dto.Message;
import com.jvyacheslav.messenger.interfaces.database_repositories.MessageRepository;
import com.jvyacheslav.messenger.service.images.Base64Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//methods for working with the database messages
import java.util.List;
@Service
public class MessageDatabaseManager {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private Base64Converter base64Converter;
    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }
    public List<Message> findAllByUsersId(String usersId){
        return messageRepository.findAllByUsersId(usersId);
    }
    public void deleteById(int id){
        messageRepository.deleteById(id);
        base64Converter.deleteImage(id);
    }
}
