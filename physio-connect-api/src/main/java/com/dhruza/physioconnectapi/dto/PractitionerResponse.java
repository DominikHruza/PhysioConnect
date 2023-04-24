package com.dhruza.physioconnectapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PractitionerResponse {
    private Long id;

    private String firstname;

    private String lastname;

    private String email;
}
