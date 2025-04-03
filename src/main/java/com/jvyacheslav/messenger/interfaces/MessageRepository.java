package com.jvyacheslav.messenger.interfaces;

import com.jvyacheslav.messenger.dto.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByUsersId(String usersId);
}