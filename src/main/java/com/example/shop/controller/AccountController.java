package com.example.shop.controller;

import com.example.shop.model.entity.User;
import com.example.shop.model.form.AddressForm;
import com.example.shop.model.form.UserInfoUpdateForm;
import com.example.shop.service.AddressService;
import com.example.shop.service.CustomerUserDetails;
import com.example.shop.service.PasswordService;
import com.example.shop.service.UserService;
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
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final PasswordService passwordService;
    private final AddressService addressService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showAccountPage(){
        return "account/account";
    }

    @GetMapping("/user")
    public String showUserPage(@AuthenticationPrincipal CustomerUserDetails userDetails, Model model){
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return "account/user/user";
    }

    @GetMapping("user/info-update")
    public String showUserInfoUpdatePage(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                         @RequestParam(value="status", required = false) String status,
                                         @ModelAttribute UserInfoUpdateForm userInfoUpdateForm,
                                         Model model){
        if(status!=null && status.equals("edited")){
            model.addAttribute("updateForm", userInfoUpdateForm);
        }else{
            model.addAttribute("updateForm", userService.findByEmail(userDetails.getUsername()));
        }
        return "account/user/info-update";
    }

    @PostMapping("/user/info-update/confirm")
    public String showUserInfoUpdateConfirmPage(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                                @Validated @ModelAttribute("updateForm") UserInfoUpdateForm userInfoUpdateForm,
                                               BindingResult bindingResult,
                                               Model model){
        if(bindingResult.hasErrors()){
            String status = "edited";
            model.addAttribute("errorMessage", "入力内容に誤りがあります。");
            return showUserInfoUpdatePage(userDetails, status, userInfoUpdateForm, model);
        }
        if(!(userInfoUpdateForm.getEmail().equals(userDetails.getUsername())) &&
            userService.findByEmail(userInfoUpdateForm.getEmail())!=null){
            String status = "edited";
            model.addAttribute("errorMessage", "入力内容をご確認ください。");
            model.addAttribute("errorMessageDuplicate", "このメールアドレスは既に使用されています。");
            return showUserInfoUpdatePage(userDetails, status, userInfoUpdateForm, model);
        }
        model.addAttribute("updateForm", userInfoUpdateForm);
        return "account/user/info-confirm";
    }

    @PostMapping("/user/info-update/complete")
    public String showUserInfoUpdateCompletePage(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                                 @Validated UserInfoUpdateForm userInfoUpdateForm,
                                                 BindingResult bindingResult,
                                                 @RequestParam(value="checkPassword", required = false) String checkPassword,
                                                 Model model){
        String email = userDetails.getUsername();
        System.out.println("username: "+userInfoUpdateForm.getUsername());
        System.out.println("email: "+userInfoUpdateForm.getEmail());
        System.out.println("phone: "+userInfoUpdateForm.getPhone());
        System.out.println("password: "+checkPassword);
        if(checkPassword==null || !(passwordService.matchesPassword(email, checkPassword))){
            model.addAttribute("errorMessage", "パスワードに誤りがあります。");
            return showUserInfoUpdateConfirmPage(userDetails, userInfoUpdateForm, bindingResult, model);
        }else{
            userService.updateUserInfo(email, userInfoUpdateForm.getUsername(),
                    userInfoUpdateForm.getEmail(), userInfoUpdateForm.getPhone());
            model.addAttribute("updateForm", userInfoUpdateForm);
            userDetails.setEmail(userInfoUpdateForm.getEmail());
            return "account/user/info-complete";
        }
    }

    @GetMapping("/user/password-update")
    public String showPasswordUpdatePage(){
        return "account/user/password-update";
    }

    @PostMapping("/user/password-update/confirm")
    public String showPasswordUpdateConfirmPage(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                                @RequestParam(value="registeredPassword", required = false) String registeredPassword,
                                                @RequestParam(value="newPassword", required = false) String newPassword,
                                                @RequestParam(value="checkPassword", required = false) String checkPassword,
                                                Model model){
        boolean matchesPassword = passwordService.matchesPassword(userDetails.getUsername(), registeredPassword);
        boolean validatePassword = passwordService.validatePassword(newPassword);
        boolean doubleCheckPassword = passwordService.doubleCheckPassword(newPassword, checkPassword);
        if(matchesPassword && validatePassword && doubleCheckPassword){
            model.addAttribute("newPassword", newPassword);
            return "account/user/password-confirm";
        }else {
            model.addAttribute("errorMessage", "入力内容に誤りがあります。");
            if (!matchesPassword) {
                model.addAttribute("errorMessageR", "パスワードに誤りがあります。");
            }
            if (!validatePassword) {
                model.addAttribute("errorMessageN", "パスワードは8～24桁で設定してください。");
            }
            if (!doubleCheckPassword) {
                model.addAttribute("errorMessageC", "新しいパスワードと確認用パスワードが一致していません。");
            }
            return showPasswordUpdatePage();
        }
    }

    @PostMapping("/user/password-update/complete")
    public String showPasswordUpdateCompletePage(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                                 @RequestParam("newPassword") String newPassword){
        userService.updatePassword(userDetails.getUsername(), newPassword);
        return "account/user/password-complete";
    }

    @GetMapping("/address")
    public String showAddressPage(@AuthenticationPrincipal CustomerUserDetails userDetails, Model model){
        Long userId = userService.getUserId(userDetails.getUsername());
        model.addAttribute("addressList", addressService.findByUserId(userId));
        return "account/address/address";
    }

    @PostMapping("/address/update")
    public String showAddressUpdatePage(@RequestParam("addressId") Long addressId,
                                        @RequestParam(value="status", required = false) String status,
                                        @ModelAttribute AddressForm form,
                                        Model model){
        if(status!=null && status.equals("edited")){
            model.addAttribute("updateForm", form);
        }else{
            model.addAttribute("updateForm", addressService.findByAddressId(addressId));
        }
        model.addAttribute("addressId", addressId);
        return "account/address/update";
    }

    @PostMapping("/address/update/confirm")
    public String showAddressUpdateConfirmPage(@RequestParam("addressId") Long addressId,
                                               @Validated @ModelAttribute("updateForm") AddressForm form,
                                               BindingResult bindingResult,
                                               Model model){
        if(bindingResult.hasErrors()){
            String status = "edited";
            model.addAttribute("errorMessage", "入力内容に誤りがあります。");
            return showAddressUpdatePage(addressId, status, form, model);
        }
        model.addAttribute("updateForm", form);
        model.addAttribute("addressId", addressId);
        return "account/address/confirm";
    }

    @PostMapping("/address/update/complete")
    public String showAddressUpdateCompletePage(@RequestParam("addressId") Long addressId,
                                                @Validated AddressForm form,
                                                BindingResult bindingResult,
                                                Model model){
        if(bindingResult.hasErrors()){
            String status = "edited";
            return showAddressUpdatePage(addressId, status, form, model);
        }
        addressService.updateAddress(addressId, form.getPostalCodeF(), form.getPostalCodeB(),
                form.getForwardAddress(), form.getLastName(), form.getFirstName(), form.getPhone());
        model.addAttribute("updateForm", form);
        return "account/address/complete";
    }

    @GetMapping("address/register")
    public String showAddressRegisterPage(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("fromAccountPage", true);
        return "redirect:/address/register";
    }

    @PostMapping("/address/delete")
    public String showAddressDeleteConfirmPage(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                               @RequestParam("addressId") Long addressId,
                                               RedirectAttributes redirectAttributes){
        addressService.deleteAddress(addressId, userDetails.getUsername());
        redirectAttributes.addFlashAttribute("message", "お届け先情報を削除しました。");
        return "redirect:/account/address";
    }

}
