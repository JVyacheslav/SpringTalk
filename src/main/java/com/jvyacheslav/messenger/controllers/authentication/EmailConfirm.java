package com.jvyacheslav.messenger.controllers.authentication;


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
        if(user.isAuth() || !(action.equals("authPage") || action.equals("regPage")) || (user.getEmailRegistrationConfirmCode() == null && user.getEmailAuthenticationConfirmCode()==null)) {
            return "redirect:/";
        } else {
            return "confirmPage";
        }
    }
    @PostMapping
    public String getForm(Model model, @ModelAttribute User user, @PathVariable String action){
        //checks the code entered by the user and if everything is OK, saves the user or authenticates him
        if(user.getUserInputConfirmCode().equals(user.getEmailRegistrationConfirmCode()) && action.equals("regPage")){
            userDatabaseManager.saveUser(user);
            user.setAuth(true);
            return "redirect:/";
        } else if(user.getUserInputConfirmCode().equals(user.getEmailAuthenticationConfirmCode()) && action.equals("authPage")){
            User realUser = userDatabaseManager.findByUsernameAndEmail(user.getUsername(), user.getEmail());
            user.setAuth(true);
            user.setPass(realUser.getPass());
            user.setId(realUser.getId());
            return "redirect:/";
        } else if(!action.equals("regPage") && !action.equals("authPage")){
            return "redirect:/";
        }else{
            model.addAttribute("validation", "Incorrect code!");
            return "confirmPage";
        }
    }
}
