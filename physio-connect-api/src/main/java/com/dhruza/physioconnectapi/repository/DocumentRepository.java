package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Set<Document> findByPatientId(Long patientId);
}
