package com.jvyacheslav.messenger.service.validation;

import com.jvyacheslav.messenger.annotations.validation.DatabaseEmailValidator;
import com.jvyacheslav.messenger.database.UserDatabaseManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

//class that provides the functionality of the DatabaseEmailValidator annotation in User class
public class EmailValidator implements ConstraintValidator<DatabaseEmailValidator, String> {
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.err.println(userDatabaseManager);
        return userDatabaseManager.findByEmail(s) == null;
    }
}