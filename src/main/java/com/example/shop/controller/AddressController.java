package com.example.shop.controller;

import com.example.shop.model.form.AddressForm;
import com.example.shop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final UserService userService;
    private final CartService cartService;
    private final AddressService addressService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showAddressPage(@AuthenticationPrincipal CustomerUserDetails userDetails, Model model){
        Long userId = userService.getUserId(userDetails.getUsername());
        model.addAttribute("addressList", addressService.findByUserId(userId));
        model.addAttribute("cartId", cartService.getCartId(userId));
        return "address/address";
    }

    @GetMapping("/redirect-to-register")
    public String redirectToRegister(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("fromAccountPage", false);
        return "redirect:/address/register";
    }

    @GetMapping("/register")
    public String showRegisterPage(@ModelAttribute AddressForm form,
                                   @ModelAttribute("fromAccountPage") boolean fromAccountPage,
                                   Model model){
        if(form!=null){
            model.addAttribute("registerForm", form);
        }
        model.addAttribute("fromAccountPage", fromAccountPage);
        model.addAttribute("linkToList", fromAccountPage ? "/account/address" : "/address");
        return "address/register";
    }

    @PostMapping("/register")
    public String showConfirmPage(@Validated @ModelAttribute("registerForm") AddressForm form,
                                  BindingResult bindingResult,
                                  @RequestParam("status") String status,
                                  @RequestParam("fromAccountPage") boolean fromAccountPage,
                                  Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage", "入力内容に誤りがあります。");
            return showRegisterPage(form, fromAccountPage, model);
        }
        if(status.equals("toRegister")){
            return showRegisterPage(form, fromAccountPage, model);
        }
        model.addAttribute("registerForm", form);
        model.addAttribute("fromAccountPage", fromAccountPage);
        return "address/confirm";
    }

    @PostMapping("register/complete")
    public String registerAddress(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                  @Validated AddressForm form,
                                  BindingResult bindingResult,
                                  @RequestParam("fromAccountPage") boolean fromAccountPage,
                                  Model model){
        if(bindingResult.hasErrors()){
            return showRegisterPage(form, fromAccountPage, model);
        }
        Long userId = userService.getUserId(userDetails.getUsername());
        addressService.addAddress(userId, form.getPostalCodeF(), form.getPostalCodeB(), form.getForwardAddress(),
                form.getLastName(), form.getFirstName(), form.getPhone());
        model.addAttribute("registerForm", form);
        model.addAttribute("cartId", cartService.getCartId(userId));
        model.addAttribute("linkToList", fromAccountPage ? "/account/address" : "/address");
        return "address/complete";
    }

    @PostMapping("/update")
    public String updateDefaultAddress(RedirectAttributes redirectAttributes,
                                     @AuthenticationPrincipal CustomerUserDetails userDetails,
                                     @RequestParam("addressId") Long addressId){
        userService.updateDefaultAddress(userDetails.getUsername(), addressId);
        redirectAttributes.addFlashAttribute("cartId",
                cartService.getCartId(userService.getUserId(userDetails.getUsername())));
        return "redirect:/checkout/redirect-to-checkout";
    }

}
