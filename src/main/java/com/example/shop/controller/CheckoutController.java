package com.example.shop.controller;

import com.example.shop.model.entity.CartItem;
import com.example.shop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final UserService userService;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final AddressService addressService;
    private final OrderService orderService;

    @PostMapping
    public String showCheckoutPage(
            @AuthenticationPrincipal CustomerUserDetails userDetails,
                                   @RequestParam("cartId") Long cartId,
                                   Model model
    ) {
        model.addAttribute("cartId", cartId);
        List<CartItem> cartItemList = cartItemService.findByCartId(cartId);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("productMap", productService.createProductMap(cartItemList));
        model.addAttribute("isInStock", cartService.isInStock(cartId));

        int subtotal = cartService.getSubtotal(cartId);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("formatSubtotal",cartService.formatWithComma(subtotal));
        model.addAttribute("postage", cartService.formatWithComma(cartService.getPostage(cartId)));
        model.addAttribute("total", cartService.formatWithComma(cartService.getTotal(cartId)));

        Long defaultAddressId = userService.findByEmail(userDetails.getUsername()).getDefaultAddressId();
        model.addAttribute("defaultAddress", addressService.findByAddressId(defaultAddressId));

        return "checkout/checkout";
    }

    @GetMapping("/redirect-to-checkout")
    public String redirectToCheckout(@ModelAttribute("cartId") Long cartId, Model model) {
        model.addAttribute("cartId", cartId);
        return "checkout/auto-post-checkout";
    }

    @PostMapping("/complete")
    public String completeCheckout(@RequestParam("cartId") Long cartId,
                                   Model model){
        List<CartItem> cartItemList = cartItemService.findByCartId(cartId);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("productMap", productService.createProductMap(cartItemList));
        model.addAttribute("total", cartService.formatWithComma(cartService.getTotal(cartId)));
        orderService.order(cartId);
        return "checkout/complete";
    }

}
