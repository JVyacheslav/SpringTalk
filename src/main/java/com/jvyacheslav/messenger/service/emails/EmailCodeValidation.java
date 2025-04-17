package com.jvyacheslav.messenger.service.emails;

import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class EmailCodeValidation {
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @Autowired
    private EmailSender emailSender;
    private User user;
    private String way;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);
    @Setter
    @Getter
    private String reason = "";
    public boolean isValidUserEmail(User user, String way){
        this.user = user;
        this.way = way;
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            //generates a simple confirmation code
            code.append(new Random().nextInt(10));
        }
        boolean result = emailSender.sendEmail(user.getEmail(), "Code", String.valueOf(code));
        if(result){
            if(way.equals("regPage")){
                user.setEmailRegistrationConfirmCode(String.valueOf(code));
            } else{
                user.setEmailAuthenticationConfirmCode(String.valueOf(code));
            }
            executorService.schedule(this::destroyUserCode, 2, TimeUnit.MINUTES);
        } else{
            setReason("Email is not valid");
        }
        return result;
    }
    public void destroyUserCode(){
        //destroys the verification code if it has not been used within 2 minutes
        System.out.println("destroyed");
        if(way.equals("regPage")){
            user.setEmailRegistrationConfirmCode(null);
        } else{
            user.setEmailAuthenticationConfirmCode(null);
        }
    }
}