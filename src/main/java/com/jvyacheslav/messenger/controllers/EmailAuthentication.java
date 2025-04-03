package com.jvyacheslav.messenger.controllers;

import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import com.jvyacheslav.messenger.service.RegistrationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authPage/emailAuth")
@SessionAttributes("user")
public class EmailAuthentication {
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @Autowired
    private RegistrationValidation registrationValidation;
    @GetMapping
    public String setForm(User user){
        if(user.isAuth()) {
            return "redirect:/";
        } else {
            return "emailAuthPage";
        }
    }

    @PostMapping
    public String getForm(@ModelAttribute User user, Model model){
        User realUser = userDatabaseManager.findByUsernameAndEmail(user.getUsername(), user.getEmail());
        if(realUser != null && registrationValidation.isValidUserEmail(user)){
            return "redirect:/authPage/confirm";
        } else{
            model.addAttribute("validation", "Incorrect login or email.");
            return "emailAuthPage";
        }
    }
}
