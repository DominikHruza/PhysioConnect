package com.dhruza.physioconnectapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface PatientDetails {
    Long getId();

    String getDiagnosis();

    String getEmail();

    String getFirstname();

    String getLastname();

}
