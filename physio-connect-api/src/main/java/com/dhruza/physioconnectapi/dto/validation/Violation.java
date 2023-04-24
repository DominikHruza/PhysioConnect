package com.dhruza.physioconnectapi.dto.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Violation {
    private final String fieldName;
    private final String message;
}
