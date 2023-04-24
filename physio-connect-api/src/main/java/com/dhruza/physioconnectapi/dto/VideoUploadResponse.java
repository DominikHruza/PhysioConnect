package com.dhruza.physioconnectapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class VideoUploadResponse {
    private String videoUrl;
}
