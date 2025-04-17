package com.jvyacheslav.messenger.controllers.authentication;

import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import com.jvyacheslav.messenger.service.emails.EmailCodeValidation;
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
    private EmailCodeValidation validation;
    @GetMapping
    public String setForm(User user){
        //
        if(user.isAuth()) {
            return "redirect:/";
        } else {
            return "emailAuthPage";
        }
    }

    @PostMapping
    public String getForm(@ModelAttribute User user, Model model){
        //checks the user's input and redirects to the confirmation page if everything is OK
        User realUser = userDatabaseManager.findByUsernameAndEmail(user.getUsername(), user.getEmail());
        if(realUser != null && user.getEmail().equals(realUser.getEmail())){
            if(validation.isValidUserEmail(user, "authPage")) {
                return "redirect:/authPage/confirm";
            }
        }
        model.addAttribute("validation", "Incorrect login or email.");
        return "emailAuthPage";
    }
}
