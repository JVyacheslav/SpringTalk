package com.jvyacheslav.messenger.controllers.registration;

import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import com.jvyacheslav.messenger.interfaces.annotation_groups.ValidationGroupCheckExists;
import com.jvyacheslav.messenger.interfaces.annotation_groups.ValidationMainGroup;
import com.jvyacheslav.messenger.service.emails.EmailCodeValidation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes(value = "user")
@RequestMapping("/regPage")
public class Registration {
    @Autowired
    private EmailCodeValidation registrationValidation;
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @Autowired
    private Validator validator;


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
        //checks the user's input and redirects to the confirmation page if everything is OK
        String validationAttribute = "validation";
        Set<ConstraintViolation<User>> valid = validator.validate(user, ValidationGroupCheckExists.class, ValidationMainGroup.class);
        List<String> errors = new ArrayList<>();
        for(ConstraintViolation<User> constraintViolation : valid){
            errors.add(constraintViolation.getMessage());
        }
        if(!errors.isEmpty()){
            model.addAttribute(validationAttribute, errors);
            return "regPage";
        } else {
            if(!registrationValidation.isValidUserEmail(user, "regPage")){
                errors.add(registrationValidation.getReason());
                model.addAttribute(validationAttribute, errors);

                return "regPage";
            }
        }
        return "redirect:/regPage/confirm";
    }
}
