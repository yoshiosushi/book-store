package com.example.shop.model.form;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressForm {

    @NotNull(message = "半角数字(0~9)を入力してください")
    @Pattern(regexp = "\\d{3}", message = "3桁の数字を入力してください")
    private String postalCodeF;    //郵便番号前3桁
    @NotNull(message = "半角数字(0~9)を入力してください")
    @Pattern(regexp = "\\d{4}", message = "4桁の数字を入力してください")
    private String postalCodeB;    //郵便番号後4桁
    @NotNull(message = "送り先住所を入力してください")
    private String forwardAddress;  //送り先住所

    @NotNull(message = "お受取人の名前を入力してください")
    private String lastName;
    @NotNull(message = "お受取人の名前を入力してください")
    private String firstName;

    @NotNull(message = "電話番号を入力してください")
    @Pattern(regexp="^[0-9\\-]+$", message = "半角数字(0~9)とハイフン(-)のみ使用してください")
    private String phone;
}
