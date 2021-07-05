package com.reed.handson.bootsecurity.validation;

import com.reed.handson.bootsecurity.domain.Transaction;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.Set;

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

    public static <T> ValidationError fromValidationErrors(Set<ConstraintViolation<T>> errors) {
        ValidationError error = new ValidationError("Validation failed. " + errors.size() + " error(s)");
        for (ConstraintViolation constraintViolation : errors) {
            if (constraintViolation instanceof FieldError) {
                FieldError fieldError = (org.springframework.validation.FieldError) constraintViolation;
                error.addValidationError(fieldError.getObjectName() + "." +  fieldError.getField() + " " + fieldError.getDefaultMessage());
            } else {
                error.addValidationError(constraintViolation.getMessage());
            }
        }
        return error;
    }
}
