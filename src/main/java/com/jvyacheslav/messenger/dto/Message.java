package com.jvyacheslav.messenger.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="messages")
public class Message {
    @Column
    private String usersId;
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int messageId;
    @Column
    private String username;
    @Column(name = "message")
    private String text;
}