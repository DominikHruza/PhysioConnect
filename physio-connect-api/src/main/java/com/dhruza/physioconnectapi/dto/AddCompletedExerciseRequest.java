package com.dhruza.physioconnectapi.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCompletedExerciseRequest {
    private Long exerciseId;
    private Integer completedSets;
    private Integer completedRepetition;
    private Integer effortLevel;
    private Integer painLevel;
    private String patientComment;
}
