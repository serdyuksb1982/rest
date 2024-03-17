package ru.serdyuk.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OrderFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderFilterValid {
    String message() default  " minCost or maxCost, to be!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
