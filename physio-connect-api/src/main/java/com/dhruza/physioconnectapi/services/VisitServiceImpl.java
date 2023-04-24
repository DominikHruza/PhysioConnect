package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.exception.DataNotFoundException;
import com.dhruza.physioconnectapi.model.Patient;
import com.dhruza.physioconnectapi.model.Practitioner;
import com.dhruza.physioconnectapi.model.Visit;
import com.dhruza.physioconnectapi.repository.PatientRepository;
import com.dhruza.physioconnectapi.repository.PractitionerRepository;
import com.dhruza.physioconnectapi.repository.VisitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private  final PatientRepository patientRepository;
    private final PractitionerRepository practitionerRepository;

    @Transactional(readOnly = true)
    public Set<Visit> getAllByPatientId(Long patientId) {
        return visitRepository.getAllByPatientId(patientId);
    }

    @Transactional(readOnly = true)
    public Set<Visit> getAllByPractitionerId(Long practitionerId) {
        return visitRepository.getAllByPractitionerId(practitionerId);
    }

    @Transactional
    public Visit addVisit(Visit visit) {
        final Practitioner practitioner = practitionerRepository
                .findById(visit.getPractitioner().getId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Practitioner with id " + visit.getPractitioner().getId() + " does not exists!"));

        final Patient patient = patientRepository
                .findById(visit.getPatient().getId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Patient with id " + visit.getPatient().getId() + " does not exists!"));

        final Visit visitNew = Visit.builder()
                .patient(patient)
                .practitioner(practitioner)
                .scheduledFor(visit.getScheduledFor())
                .build();

        visitRepository.save(visitNew);

        return visitNew;
    }

    @Transactional
    public Visit rescheduleVisit(Visit visit) {
        final Visit visitForUpdate = visitRepository
                .findById(visit.getId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Visit with id " + visit.getId() + " does not exists!"));
        visitForUpdate.setScheduledFor(visit.getScheduledFor());
        return visitForUpdate;
    }

    @Transactional
    public void deleteVisit(Long id) {
        final Visit visitForDelete = visitRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        "Visit with id " + id + " does not exists!"));
       visitRepository.delete(visitForDelete);
    }
}
