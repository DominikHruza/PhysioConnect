package com.dhruza.physioconnectapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompletedExerciseResponse {
    private Long id;
    private Long exerciseId;
    private String name;
    private Integer completedSets;
    private Integer completedRepetition;
    private Integer effortLevel;
    private Integer painLevel;
    private String patientComment;
}
