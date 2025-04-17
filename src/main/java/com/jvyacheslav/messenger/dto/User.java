package com.jvyacheslav.messenger.dto;


import com.jvyacheslav.messenger.annotations.validation.DatabaseEmailValidator;
import com.jvyacheslav.messenger.annotations.validation.DatabaseNameValidator;
import com.jvyacheslav.messenger.interfaces.annotation_groups.ValidationGroupCheckExists;
import com.jvyacheslav.messenger.interfaces.annotation_groups.ValidationMainGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Table(name="users")
@Entity
@NoArgsConstructor
@ToString
@Component
public class User {
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Column
    @Size(min = 2, max = 30, message = "Incorrect length of username", groups = ValidationMainGroup.class)
    @DatabaseNameValidator(message = "User with this username already exists", groups = ValidationGroupCheckExists.class)
    private String username;
    @Column
    @Size(min = 5, max = 30, message = "Incorrect length of password", groups = ValidationMainGroup.class)
    private String pass;
    @Column(unique = true)
    @Email(message = "Incorrect email", groups = ValidationMainGroup.class)
    @NotNull(message = "You didn't specify an email", groups = ValidationMainGroup.class)
    @DatabaseEmailValidator(message = "User with this email already exists", groups = ValidationGroupCheckExists.class)
    private String email;
    @Transient
    private boolean auth;
    @Transient
    private String usersId;
    @Transient
    private String emailRegistrationConfirmCode;
    @Transient
    private String emailAuthenticationConfirmCode;
    @Transient
    private String userInputConfirmCode;
    public void clearUser(){
        //clears user
        id = 0;
        username=null;
        pass=null;
        email=null;
        auth=false;
        emailRegistrationConfirmCode=null;
        emailAuthenticationConfirmCode=null;
        userInputConfirmCode=null;
    }
}
