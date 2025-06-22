package com.example.shop.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private Long userId;

    private String postalCodeF;    //郵便番号前3桁
    private String postalCodeB;    //郵便番号後4桁
    private String forwardAddress;  //送り先住所

    private String lastName;
    private String firstName;

    private String phone;
}
