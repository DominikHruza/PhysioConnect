package com.dhruza.physioconnectapi.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VisitResponse {
   private Integer id;
    private PatientResponse patient;
    private PractitionerResponse practitioner;
    private LocalDateTime scheduledFor;
}
