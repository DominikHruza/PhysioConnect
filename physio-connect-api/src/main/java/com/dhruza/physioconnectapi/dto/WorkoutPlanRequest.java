package com.dhruza.physioconnectapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WorkoutPlanRequest {
    private Long patientId;
    private Long practitionerId;
    private Set<ExerciseDto> exercises = new HashSet<>();
    private LocalDate startAt;
    private LocalDate endAt;
    private String description;
    private Integer effortLevel;
    private Integer painLevel;
    private Boolean isActive;
}

