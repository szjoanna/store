package io.asia.store.validator.impl;

import io.asia.store.domain.dto.UserDto;
import io.asia.store.validator.PasswordValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, UserDto> {
    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getConfirmPassword());
    }
}
