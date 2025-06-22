package com.example.shop.model.form;

import com.example.shop.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoUpdateForm {

    @NotNull(message = "ユーザ名を入力してください")
    private String username;

    @NotNull(message = "メールアドレスを入力してください")
    @Email
    private String email;

    @NotNull(message = "電話番号を入力してください")
    @Pattern(regexp="^[0-9\\-]+$", message = "半角数字(0~9)とハイフン(-)のみ使用してください")
    private String phone;

}
