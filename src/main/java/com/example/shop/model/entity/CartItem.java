package com.example.shop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    @Id                                                 //主キー
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自動採番
    private Long cartItemId;
    private Long cartId;
    private Long productId;
    @Min(1)
    private Integer quantity;
}
