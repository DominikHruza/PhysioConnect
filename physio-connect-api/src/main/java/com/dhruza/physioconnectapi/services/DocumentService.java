package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.model.Document;

import java.util.Set;

public interface DocumentService {
    Set<Document> findPatientDocuments(Long patientId);
    Document addDocument(Long patientId, String filePath, String name);
    Document findById(Long documentId);
}
