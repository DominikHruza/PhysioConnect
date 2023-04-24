package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Set<Visit> getAllByPatientId(Long patientId);

    @Query(value = "SELECT v FROM Visit v " +
            "WHERE v.practitioner.id = :practitionerId " +
            "AND v.scheduledFor > CURRENT_TIMESTAMP")
    Set<Visit> getAllByPractitionerId(Long practitionerId);
}
