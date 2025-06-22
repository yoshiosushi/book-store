package com.example.shop.controller;

import com.example.shop.model.entity.Cart;
import com.example.shop.model.entity.CartItem;
import com.example.shop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final UserService userService;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final ProductService productService;

    @GetMapping
    public String showCartPage(@AuthenticationPrincipal CustomerUserDetails userDetails, Model model) {
        Cart cart = cartService.findByUserId(
                userService.findByEmail(userDetails.getUsername()).getUserId());
        List<CartItem> cartItemList = cartItemService.findByCartId(cart.getCartId());
        model.addAttribute("cartId", cart.getCartId());
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("productMap", productService.createProductMap(cartItemList));
        model.addAttribute("subtotal",
                cartService.formatWithComma(cartService.getSubtotal(cart.getCartId())));

        return "cart/cart";
    }

    @PostMapping("/add")
    public String addCartItem(@AuthenticationPrincipal CustomerUserDetails userDetails,
                              @RequestParam("productId") Long productId,
                              @RequestParam("quantity") Integer quantity){
        Long cartId = cartService.getCartId(userService.getUserId(userDetails.getUsername()));
        cartItemService.addCartItem(cartId, productId, quantity);

        return "cart/add";
    }

    @PostMapping("/update")
    public String updateCartItem(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                 @RequestParam("productId") Long productId,
                                 @RequestParam("quantity") Integer quantity){
        Long cartId = cartService.getCartId(userService.getUserId(userDetails.getUsername()));
        cartItemService.updateCartItem(cartId, productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/itemDelete/{cartItemId}")
    public String deleteCartItem(@PathVariable("cartItemId") Long cartItemId){
        cartItemService.deleteCartItem(cartItemId);
        return "redirect:/cart";
    }

}
