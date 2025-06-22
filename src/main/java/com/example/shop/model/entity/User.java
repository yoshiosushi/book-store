package com.example.shop.model.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private Long userId;
    private String username;
    private String email;
    private String phone;
    private String password;
    private Authority authority;
    public enum Authority{
        ADMIN,
        USER
    }
    private Long defaultAddressId;
}