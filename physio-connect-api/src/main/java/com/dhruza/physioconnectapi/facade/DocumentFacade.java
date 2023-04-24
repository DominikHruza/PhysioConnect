package com.dhruza.physioconnectapi.facade;

import com.dhruza.physioconnectapi.dto.DocumentResource;
import com.dhruza.physioconnectapi.model.Document;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentFacade {
    Document uploadDocument(Long patientId, MultipartFile file);
    DocumentResource downloadDocument(Long documentId);
}
