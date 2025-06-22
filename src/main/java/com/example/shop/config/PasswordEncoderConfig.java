package com.example.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder(){
        String secret = ""; // または環境変数などで管理する秘密キー
        int saltLength = 16;
        int iterations = 185000;
        Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm algorithm =
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256;

        return new Pbkdf2PasswordEncoder(secret, saltLength, iterations, algorithm);
    }
}
