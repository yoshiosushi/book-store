package com.example.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private Long productId;
    private String productName;
    private Integer stock;
    private Integer price;
    private String note;
    private String image;   //ゆくゆくList<String>に変更
}
