package ru.serdyuk.validation;

import org.apache.commons.lang3.ObjectUtils;
import ru.serdyuk.web.model.OrderFilter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderFilterValidValidator implements ConstraintValidator<OrderFilterValid, OrderFilter> {
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }


        if ((value.getMinCost() == null && value.getMaxCost() != null)
                || (value.getMinCost() != null && value.getMaxCost() == null)) {
            return false;
        }
        return true;
    }
}
