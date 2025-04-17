package com.jvyacheslav.messenger.service.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender extends EmailSettings {
    @Autowired
    private Environment environment;
    public boolean sendEmail(String email, String subject, String text){
        try{
            //Sends a template email to the user
            Session session = super.openEmailSenderSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getProperty("email")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
