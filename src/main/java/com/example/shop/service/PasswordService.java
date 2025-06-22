package com.example.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= 8 && password.length() <= 24;
    }
    public boolean matchesPassword(String email, String password){
        if (password == null) {
            return false;
        }
        String registeredPassword = userService.findByEmail(email).getPassword();
        return passwordEncoder.matches(password, registeredPassword);
    }

    public boolean doubleCheckPassword(String password, String checkPassword){
        if (password == null || checkPassword == null) {
            return false;
        }
        return password.equals(checkPassword);
    }
}
