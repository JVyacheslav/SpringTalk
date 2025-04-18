package com.jvyacheslav.messenger.database;

import com.jvyacheslav.messenger.dto.Chat;
import com.jvyacheslav.messenger.interfaces.database_repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//methods for working with the database chats
@Service
public class ChatDatabaseManager {
    @Autowired
    private ChatRepository chatRepository;
    public Chat getChatByUsersId(String usersId){
        return chatRepository.findByUsersId(usersId);
    }
    public Chat save(Chat chat){
        return chatRepository.save(chat);
    }
}
