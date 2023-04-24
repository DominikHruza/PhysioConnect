package com.dhruza.physioconnectapi.services;


import com.dhruza.physioconnectapi.model.Visit;

import java.util.Set;

public interface VisitService {
    Set<Visit> getAllByPatientId(Long patientId);
    Set<Visit> getAllByPractitionerId(Long practitionerId);
    Visit addVisit(Visit visit);
    Visit rescheduleVisit(Visit visit);
    void deleteVisit(Long id);
}
