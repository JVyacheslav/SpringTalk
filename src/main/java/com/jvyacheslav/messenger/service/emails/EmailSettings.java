package com.jvyacheslav.messenger.service.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
@Component
public class EmailSettings {
    @Autowired
    private Environment environment;
    private final Properties emailProperties = new Properties();
    EmailSettings(){
        //creates properties
        emailProperties.put("mail.smtp.port", "465");
        emailProperties.put("mail.smtp.host", "smtp.gmail.com");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.ssl.enable", "true");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.required", "true");
        emailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");

    }
    protected Session openEmailSenderSession() throws MessagingException {
        //performs authentication on the smtp server
        Session session = Session.getInstance(emailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(environment.getProperty("email"), environment.getProperty("pass"));
            }
        });
        session.setDebug(true);
        return session;
    }
}
