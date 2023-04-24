package com.dhruza.physioconnectapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkoutPlanResponse {
    private Integer id;
    private Long practitionerId;
    private Long patientId;
    private LocalDate startAt;
    private LocalDate endAt;
    private String description;
    private Integer effortLevel;
    private Integer painLevel;
    private Boolean isActive;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<ExerciseDto> exercises = new HashSet<>();
}
