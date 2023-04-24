package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.exception.DataNotFoundException;
import com.dhruza.physioconnectapi.model.Document;
import com.dhruza.physioconnectapi.model.Patient;
import com.dhruza.physioconnectapi.repository.DocumentRepository;
import com.dhruza.physioconnectapi.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public Document findById(Long documentId) {
        return documentRepository.findById(documentId).
                orElseThrow(() -> new DataNotFoundException(
                        "Document with id " + documentId + " not found!"
                ));
    }

    @Transactional(readOnly = true)
    public Set<Document> findPatientDocuments(Long patientId) {
        return documentRepository.findByPatientId(patientId);
    }

    @Transactional
    public Document addDocument(Long patientId, String filePath, String name) {
        final Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Patient with id " + patientId + " does not exists!"));

        final Document document = Document.builder()
                .patient(patient)
                .addedAt(LocalDateTime.now())
                .urlLocation(filePath)
                .addedAt(LocalDateTime.now())
                .fileName(name)
                .build();
        return documentRepository.save(document);
    }
 }
