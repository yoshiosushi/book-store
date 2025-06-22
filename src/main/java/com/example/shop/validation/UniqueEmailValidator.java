package com.example.shop.validation;

import com.example.shop.repository.UserRepository;
import com.example.shop.service.CustomerUserDetails;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email == null || email.isEmpty()) return true;
        return userRepository.findByEmail(email).isEmpty(); // 非nullならfalseを返す＝このemailは使えない
    }

}
