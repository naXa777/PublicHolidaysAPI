package com.avk.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintViolation;
import java.beans.Introspector;
import java.util.Locale;

@JsonSerialize
public class SimpleObjectError {
    public String defaultMessage;
    public String objectName;
    public String field;
    public Object rejectedValue;
    public String code;

    public static SimpleObjectError from(ConstraintViolation<?> violation, MessageSource msgSrc, Locale locale) {
        SimpleObjectError result = new SimpleObjectError();
        result.defaultMessage = msgSrc.getMessage(violation.getMessageTemplate(),
                new Object[]{violation.getLeafBean().getClass().getSimpleName(), violation.getPropertyPath().toString(),
                        violation.getInvalidValue()}, violation.getMessage(), locale);
        result.objectName = Introspector.decapitalize(violation.getRootBean().getClass().getSimpleName());
        result.field = String.valueOf(violation.getPropertyPath());
        result.rejectedValue = violation.getInvalidValue();
        result.code = violation.getMessageTemplate();
        return result;
    }
}
