package com.dhruza.physioconnectapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExerciseDto {
    private Long id;
    private String exrxName;
    private Integer exrxSets;
    private Integer exrxRepetition;
    private String videoInstrUrl;
    private String description;
    private Integer effortLevel;
    private Integer painLevel;
    private String patientComment;
    private Boolean isCompleted;
}
