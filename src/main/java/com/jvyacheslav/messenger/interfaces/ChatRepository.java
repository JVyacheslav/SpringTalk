package com.jvyacheslav.messenger.interfaces;

import com.jvyacheslav.messenger.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByUsersId(String usersId);
}
