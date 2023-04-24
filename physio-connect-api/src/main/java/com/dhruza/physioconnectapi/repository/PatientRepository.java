package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.dto.PatientDetails;
import com.dhruza.physioconnectapi.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = " SELECT p.*, au.firstname, au.lastname, au.email " +
            "FROM patients p " +
            "INNER JOIN app_users au on au.id = p.id " +
            "WHERE p.practitioner_id = :practitionerId ;",
            nativeQuery = true)
    Set<PatientDetails> getAllPatientWithDetails(Long practitionerId);

    PatientDetails findPatientById(Long patientId);

   Optional<Patient> findPatientByEmailAndRegistrationCode(String email, String registrationCode);
}
