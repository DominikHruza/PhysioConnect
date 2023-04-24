package com.dhruza.physioconnectapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPatientRequest {

    @NotNull
    private Long practitionerId;

    @NotNull
    @NotBlank
    private String firstname;

    @NotNull
    @NotBlank
    private String lastname;

    @Email
    private String email;

    @NotNull
    @NotBlank
    private String diagnosis;
}
