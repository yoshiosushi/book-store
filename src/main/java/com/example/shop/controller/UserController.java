package com.example.shop.controller;

import com.example.shop.model.form.UserForm;
import com.example.shop.service.PasswordService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordService passwordService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/register")
    public String showRegisterPage(@ModelAttribute UserForm form, Model model){
        if(form!=null){
            model.addAttribute("registerForm", form);
        }
        return "user/register";
    }

    @PostMapping("/register")
    public String showRegisterConfirmPage(@Validated @ModelAttribute("registerForm") UserForm form,
                                          BindingResult bindingResult,
                                          @RequestParam(value="checkPassword", required = false) String checkPassword,
                                          Model model){
        boolean matchesPassword = passwordService.doubleCheckPassword(form.getPassword(), checkPassword) && checkPassword != null;
        if(!bindingResult.hasErrors() && matchesPassword){
            model.addAttribute("registerForm", form);
            model.addAttribute("maskedPassword", "*".repeat(form.getPassword().length()));
            return "user/confirm";
        }else{
            if(bindingResult.hasErrors()){
                model.addAttribute("errorMessage", "入力内容に誤りがあります。");
            }
            if(!matchesPassword){
                model.addAttribute("errorMessageC", "登録するパスワードと確認用パスワードが一致していません。");
            }
            return showRegisterPage(form, model);
        }
    }

    @PostMapping("register/complete")
    public String showRegisterCompletePage(@Validated UserForm form, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return showRegisterPage(form, model);
        }
        userService.registerUser(form.getUsername(), form.getEmail(), form.getPhone(), form.getPassword());
        model.addAttribute("registerForm", form);
        model.addAttribute("maskedPassword", "*".repeat(form.getPassword().length()));
        return "user/complete";
    }

}
