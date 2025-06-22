package com.example.shop.service;

import com.example.shop.model.entity.User;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    public User findByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public Long getUserId(String email){
        return findByEmail(email).getUserId();
    }

    public void registerUser(String username, String email, String phone, String password){
        Long defaultAddressId = null;
        userRepository.insertUser(username, email, phone, passwordEncoder.encode(password), defaultAddressId);
        cartService.registerCart(userRepository.findByEmail(email).get().getUserId());
    }

    public void updateUserInfo(String email, String userName, String newEmail, String phone){
        userRepository.updateUserInfo(getUserId(email), userName, newEmail, phone);
    }

    public void updatePassword(String email, String password){
        userRepository.updatePassword(getUserId(email), passwordEncoder.encode(password));
    }

    public void updateDefaultAddress(String email, Long defaultAddressId){
        userRepository.updateDefaultAddress(getUserId(email), defaultAddressId);
    }


}
