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
    @Column(length = 30)
    private String username;
    @Column(name = "message" , length = 8000)
    private String text;
    @Column
    private boolean attachment;
    @Transient
    private String type = "save";
    @Transient
    private String img;
}