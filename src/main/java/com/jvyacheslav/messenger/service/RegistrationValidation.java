package com.jvyacheslav.messenger.service;

import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RegistrationValidation {
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @Autowired
    private EmailSender emailSender;
    @Setter
    @Getter
    private String reason;
    public boolean isInValidUserData(User user){
        return !(isValidUserName(user.getUsername())
                && isValidUserPassword(user.getPass())
                && isValidUserEmail(user)
                && doesNotUsernameExist(user.getUsername())
                && doesNotEmailExist(user.getEmail()));
    }
    private boolean isValidUserName(String username){
        boolean isValidUsername = username.length() > 2 && username.length() < 31;
        if(isValidUsername){
            reason+="Incorrect username\n";
        }
        return isValidUsername;
    }
    private boolean isValidUserPassword(String password){
        boolean isValidUserPassword = password.length() > 5 && password.length() < 31;
        if(!isValidUserPassword){
            reason+="Incorrect password\n";
        }
        return isValidUserPassword;
    }
    public boolean isValidUserEmail(User user){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(new Random().nextInt(10));
        }
        boolean result = emailSender.sendEmail(user.getEmail(), "Code", String.valueOf(code));
        if(result){
            user.setEmailConfirmCode(String.valueOf(code));
        } else{
            reason+="Email is not valid\n";
        }
        return result;
    }
    private boolean doesNotUsernameExist(String username){
        boolean doesUsernameExist = userDatabaseManager.findByEmail(username) == null;
        if(doesUsernameExist){
            reason+="User with this username already exists";
        }
        return !doesUsernameExist;
    }
    private boolean doesNotEmailExist(String email){
        boolean doesEmailExist = userDatabaseManager.findByEmail(email) == null;
        if(doesEmailExist){
            reason+="User whit this email already exists";
        }
        return !doesEmailExist;
    }
}
