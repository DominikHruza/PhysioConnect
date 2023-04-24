package com.dhruza.physioconnectapi.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.Tika;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class DocumentValidator implements ConstraintValidator<ValidDocument, MultipartFile> {
    private final Long VALID_SIZE = 5000000L;

    @Override
    public void initialize(ValidDocument constraintAnnotation) {}

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context){
        if(!isValidMediaType(file)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Document type not supported!")
                    .addConstraintViolation();
            return false;
        }

        if(!isValidSize(file)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Document file size is too big (>5mb)!")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isValidMediaType(MultipartFile file){
        try {
            Tika tika = new Tika();
            final String detect = tika.detect(file.getBytes());
            MediaType.parseMediaType(detect);
            return true;
        } catch (InvalidMediaTypeException | IOException e) {
            return false;
        }
    }

    private boolean isValidSize(MultipartFile file){
        return file.getSize() <= VALID_SIZE;
    }
}
