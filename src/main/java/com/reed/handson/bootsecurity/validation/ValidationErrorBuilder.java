package com.reed.handson.bootsecurity.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ValidationErrorBuilder {

    public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (org.springframework.validation.FieldError) objectError;
                error.addValidationError(fieldError.getObjectName() + "." +  fieldError.getField() + " " + fieldError.getDefaultMessage());
            } else {
                error.addValidationError(objectError.getObjectName() + " "  + objectError.getDefaultMessage());
            }
        }
        return error;
    }
}
