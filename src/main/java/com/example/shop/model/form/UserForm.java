package com.example.shop.model.form;

import com.example.shop.validation.UniqueEmail;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserForm {

    @NotNull(message = "ユーザ名を入力してください")
    private String username;

    @NotNull(message = "メールアドレスを入力してください")
    @UniqueEmail
    @Email
    private String email;

    @NotNull(message = "電話番号を入力してください")
    @Pattern(regexp="^[0-9\\-]+$", message = "半角数字(0~9)とハイフン(-)のみ使用してください")
    private String phone;

    @NotNull(message = "パスワードは8～24桁で設定してください")
    @Size(min= 8, max = 24, message = "パスワードは8～24桁で設定してください")
    private String password;

}
