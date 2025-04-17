package com.jvyacheslav.messenger.controllers.authentication;


import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("user")
@RequestMapping("/authPage")
public class PasswordAuthentication {
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @GetMapping
    public String setForm(User user){
        if(user.isAuth()) {
            return "redirect:/";
        } else{
            return "authPage";
        }
    }
    @PostMapping
    public String getForm(@ModelAttribute User user, Model model){
        //checks the user's data for a match in the database, if everything is OK - authenticates the user
        User realUser = userDatabaseManager.findByUsernameAndPass(user.getUsername(), user.getPass());
        if(realUser != null){
            user.setEmail(realUser.getEmail());
            user.setId(realUser.getId());
            user.setAuth(true);
            return "redirect:/";
        } else{
            model.addAttribute("validation", "Incorrect login or password.");
            return "authPage";
        }
    }

}