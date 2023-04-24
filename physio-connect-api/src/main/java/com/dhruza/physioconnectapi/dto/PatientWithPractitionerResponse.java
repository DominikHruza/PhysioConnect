package com.dhruza.physioconnectapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PatientWithPractitionerResponse {
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String diagnosis;

    private PractitionerResponse practitioner;
}
