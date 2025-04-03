package com.jvyacheslav.messenger.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Table(name="users")
@Entity
@NoArgsConstructor
@ToString
public class User {
    @Column
    private String username;
    @Column
    private String pass;
    @Id
    @Column
    private String email;
    @Transient
    private boolean auth;
    @Transient
    private String usersId;
    @Transient
    private String emailConfirmCode;
    @Transient
    private String userInputConfirmCode;
}
