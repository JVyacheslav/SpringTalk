package com.jvyacheslav.messenger.interfaces.database_repositories;

import com.jvyacheslav.messenger.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//repository for working with the database chats
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByUsersId(String usersId);
}
