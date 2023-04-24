package com.dhruza.physioconnectapi.dto;

import lombok.*;
import org.springframework.core.io.ByteArrayResource;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentResource {
    ByteArrayResource byteArrayResource;
    String fileName;
    String fileExtension;
}
