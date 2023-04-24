package com.dhruza.physioconnectapi.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DocumentValidator.class})
public @interface ValidDocument {
    String message() default "Invalid file!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
