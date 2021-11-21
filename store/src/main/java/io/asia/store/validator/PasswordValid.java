package io.asia.store.validator;

import io.asia.store.validator.impl.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

//oznaczenie adnotacji
@Documented
//adnotacja wykorzystywan atylko dla typow, czyli mozna nia oznaczac tylko klasy
@Target(ElementType.TYPE)
//adnotacja bedzie dzialac w trakcie dzialania programu
@Retention(RetentionPolicy.RUNTIME)
//podlaczmay password validator do adnotacji
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {
    String message() default "Password and confirm password can not be different";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
