package com.example.shop.service;

import com.example.shop.model.entity.CartItem;
import com.example.shop.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> findByCartId(Long cartId){
         return cartItemRepository.findByCartId(cartId);
    }

    public void addCartItem(Long cartId, Long productId, Integer quantity){
        Optional<CartItem> cartItemOpt = cartItemRepository.findByProductId(cartId, productId);
        if(cartItemOpt.isEmpty()){
            cartItemRepository.insertCartItem(cartId, productId, quantity);
        }else{
            cartItemRepository.addCartItemQuantity(cartItemOpt.get().getCartItemId(), quantity);
        }
    }

    public void updateCartItem(Long cartId, Long productId, Integer quantity){
        Long cartItemId = cartItemRepository.findByProductId(cartId, productId).get().getCartItemId();
        if(quantity<=0){
            deleteCartItem(cartItemId);
        }
        cartItemRepository.updateCartItemQuantity(cartItemId, quantity);
    }

    public void deleteCartItem(Long cartItemId){
        cartItemRepository.deleteCartItem(cartItemId);
    }

}
