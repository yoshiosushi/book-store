package com.example.shop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "このメールアドレスは既に使用されています。";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
