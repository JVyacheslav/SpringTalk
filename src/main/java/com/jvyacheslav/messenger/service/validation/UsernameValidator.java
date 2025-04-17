package com.jvyacheslav.messenger.service.validation;

import com.jvyacheslav.messenger.annotations.validation.DatabaseNameValidator;
import com.jvyacheslav.messenger.database.UserDatabaseManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
//class that provides the functionality of the DatabaseNameValidator annotation in User class
public class UsernameValidator implements ConstraintValidator<DatabaseNameValidator, String> {
    @Autowired
    private UserDatabaseManager userDatabaseManager;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userDatabaseManager.findByUsername(s) == null;
    }
}
