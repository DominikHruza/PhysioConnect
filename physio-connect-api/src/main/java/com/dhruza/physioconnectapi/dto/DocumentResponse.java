package com.dhruza.physioconnectapi.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DocumentResponse {
    private Integer id;
    private Long patientId;
    private String urlLocation;
    private LocalDateTime addedAt;
    private String fileName;
}
