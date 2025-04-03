package com.jvyacheslav.messenger.service;

import com.jvyacheslav.messenger.dto.User;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender extends EmailSettings {
    public boolean sendEmail(String email, String subject, String text){
        try{
            Session session = super.openEmailSenderSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(super.senderProperties.getProperty("email")));
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
