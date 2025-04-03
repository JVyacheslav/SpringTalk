package com.jvyacheslav.messenger.service;

import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Component
public class EmailSettings {
    private final Properties emailProperties = new Properties();
    protected final Properties senderProperties = new Properties();
    EmailSettings(){
        try(InputStream inputEmailProps = new FileInputStream("src/main/resources/email.properties");
            InputStream inputSecretProps = new FileInputStream("src/main/resources/secret.properties")) {
            emailProperties.load(inputEmailProps);
            senderProperties.load(inputSecretProps);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected Session openEmailSenderSession() throws MessagingException {
        Session session = Session.getInstance(emailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderProperties.getProperty("email"), senderProperties.getProperty("pass"));
            }
        });
        session.setDebug(true);
        return session;
    }
}
