package com.example.shop.service;

import com.example.shop.model.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemService cartItemService;
    private final ProductService productService;

    public void order(Long cartId) {
        List<CartItem> cartItemList = cartItemService.findByCartId(cartId);
        for (CartItem cartItem : cartItemList) {
            productService.updateProductStock(cartItem.getProductId(), cartItem.getQuantity());
            cartItemService.deleteCartItem(cartItem.getCartItemId());
        }
    }

}
