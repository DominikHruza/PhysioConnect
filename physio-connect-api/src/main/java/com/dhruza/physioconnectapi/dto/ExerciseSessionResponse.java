package com.dhruza.physioconnectapi.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExerciseSessionResponse {
    Long id;
    private Long planId;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String patientComment;
    Set<CompletedExerciseResponse> completedExercises;
}
