package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.dto.PatientDetails;
import com.dhruza.physioconnectapi.model.Patient;

import java.util.Set;

public interface PatientService {
    Patient add(Patient patient);

    Set<PatientDetails> findWithNextVisitAndActivePlanCount(Long practitionerId);

    PatientDetails findPatientWithDetails(Long patientId);
}
