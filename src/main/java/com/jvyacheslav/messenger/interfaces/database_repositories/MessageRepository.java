package com.jvyacheslav.messenger.interfaces.database_repositories;

import com.jvyacheslav.messenger.dto.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//repository for working with the database messages
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByUsersId(String usersId);
}