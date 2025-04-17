package com.jvyacheslav.messenger.annotations.validation;

import com.jvyacheslav.messenger.service.validation.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//annotation for checking username for presence in the database
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface DatabaseNameValidator {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
