package com.example.shop.controller;

import com.example.shop.service.CustomerUserDetails;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ProductService productService;

    @GetMapping("/")
    public String redirectHomePage(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal CustomerUserDetails userDetails,
                               @RequestParam(value = "keyword",required = false) String keyword, Model model){
        model.addAttribute("username", userDetails.getDisplayUsername());
        if(keyword != null && !keyword.isEmpty()) {
            model.addAttribute("productList", productService.findByKeyword(keyword));
            model.addAttribute("keyword", keyword);
        }else {
            model.addAttribute("productList", productService.findAll());
        }
        return "index/home";
    }

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        if (error!=null) {
            model.addAttribute("errorMessage", "認証に失敗しました");
        }
        return "index/login";
    }

    @GetMapping("/logout")
    public String showLogoutPage(){
        return "index/logout";
    }

}
