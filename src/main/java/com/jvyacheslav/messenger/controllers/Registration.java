package com.jvyacheslav.messenger.controllers;

import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import com.jvyacheslav.messenger.service.RegistrationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes(value = "user")
@RequestMapping("/regPage")
public class Registration {
    @Autowired
    private RegistrationValidation registrationValidation;
    @Autowired
    private UserDatabaseManager userDatabaseManager;


    @GetMapping
    public String setForm(User user){
        System.out.println(user.toString());
        if(user.isAuth()) {
            return "redirect:/";
        } else{
            return "regPage";
        }
    }


    @PostMapping
    public String getForm(@ModelAttribute @SessionAttribute User user, Model model){
        String validationAttribute = "validation";
        if(registrationValidation.isInValidUserData(user)){
            model.addAttribute(validationAttribute, registrationValidation.getReason());
            return "regPage";
        } else {
            return "redirect:/regPage/confirm";
        }
    }
}
