package com.jvyacheslav.messenger.controllers;


import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("user")
@RequestMapping("/{action}/confirm")
public class EmailConfirm {
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @GetMapping
    public String setForm(User user, @PathVariable String action){
        if(user.isAuth() || !(action.equals("authPage") || action.equals("regPage")) || user.getEmailConfirmCode() == null) {
            return "redirect:/";
        } else {
            return "confirmPage";
        }
    }
    @PostMapping
    public String getForm(Model model, @ModelAttribute User user, @PathVariable String action){
        if(user.getUserInputConfirmCode().equals(user.getEmailConfirmCode())){
            user.setAuth(true);
            user.setEmailConfirmCode(null);
            if(action.equals("regPage")) {
                userDatabaseManager.saveUser(user);
            } else {
                User realUser = userDatabaseManager.findByUsernameAndEmail(user.getUsername(), user.getEmail());
                user.setPass(realUser.getPass());
            }
            return "redirect:/";
        } else{
            model.addAttribute("validation", "Incorrect code!");
            return "confirmPage";
        }
    }
}
