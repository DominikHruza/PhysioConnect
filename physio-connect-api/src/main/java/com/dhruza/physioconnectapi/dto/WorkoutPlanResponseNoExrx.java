package com.dhruza.physioconnectapi.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkoutPlanResponseNoExrx {
    private Integer id;
    private Long practitionerId;
    private Long patientId;
    private LocalDate startAt;
    private LocalDate endAt;
    private String description;
    private Integer effortLevel;
    private Integer painLevel;
    private Boolean isActive;
}
