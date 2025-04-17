package com.jvyacheslav.messenger.service.images;


import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class Base64Converter {
    public void saveBase64StringAsImage(String img, int id) throws IOException {
        //convert base64 string to image and save it
        FileOutputStream fileOutputStream = new FileOutputStream(getFile(id));
        fileOutputStream.write(Base64.getDecoder().decode(img));
    }
    public String getImageAsBase64String(int id) throws IOException{
        //convert image to base64 and return it
        File file = getFile(id);
        if(file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            return new String(Base64.getEncoder().encodeToString(fileInputStream.readAllBytes()));
        } else{
            return null;
        }
    }
    private File getFile(int id){
        //checks if the project directory exists and if not, creates it
        File directory = new File(System.getProperty("user.home"), "messagesAttachments");
        if (!directory.exists()) {
            directory.mkdir();
        }
        //returns file
        return new File(System.getProperty("user.home")+"/messagesAttachments/"+id+".png");
    }
    public void deleteImage(int id){
        if(getFile(id).exists()){
            getFile(id).delete();
        }
    }
}