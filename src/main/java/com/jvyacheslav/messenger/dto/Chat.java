package com.jvyacheslav.messenger.dto;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="chats")
@ToString
public class Chat {
    @Id
    @Column
    private String usersId;
}