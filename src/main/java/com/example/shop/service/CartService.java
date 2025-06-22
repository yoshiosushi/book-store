package com.example.shop.service;

import com.example.shop.model.entity.Cart;
import com.example.shop.model.entity.CartItem;
import com.example.shop.model.entity.Product;
import com.example.shop.repository.CartRepository;
import com.example.shop.util.PriceFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;

    public Cart findByUserId(Long userId){
        return cartRepository.findByUserId(userId);
    }

    public void registerCart(Long userId){
        cartRepository.insertCart(userId);
    }

    public Long getCartId(Long userId){
        return cartRepository.findByUserId(userId).getCartId();
    }

    public int getSubtotal(Long cartId) {
        List<CartItem> cartItemList = cartItemService.findByCartId(cartId);
        int subtotal = 0;
        for (CartItem cartItem : cartItemList) {
            Product product = productService.findById(cartItem.getProductId());
            subtotal += product.getPrice() * cartItem.getQuantity();
        }
        return subtotal;
    }

    public int getPostage(Long cartId){
        int subtotal = getSubtotal(cartId);
        return (subtotal>=5000 || subtotal==0) ? 0 : 1000;
    }

    public int getTotal(Long cartId){
        return getSubtotal(cartId) + getPostage(cartId);
    }

    public String formatWithComma(int number){
        return PriceFormat.formatWithComma(number);
    }

    public boolean isInStock(Long cartId){
        boolean isInStock = true;
        List<CartItem> cartItemList = cartItemService.findByCartId(cartId);
        for(CartItem cartItem : cartItemList){
            if(cartItem.getQuantity() > productService.findById(cartItem.getProductId()).getStock()){
                isInStock = false;
                break;
            }
        }
        return isInStock;
    }

}
