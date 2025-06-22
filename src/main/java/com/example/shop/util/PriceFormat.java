package com.example.shop.util;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormat {
        public static String formatWithComma(int number) {
            return NumberFormat.getInstance(Locale.JAPAN).format(number);
        }
}
