package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.auth.model.RoleType;
import com.dhruza.physioconnectapi.auth.repository.AppUserRepository;
import com.dhruza.physioconnectapi.auth.service.SignInService;
import com.dhruza.physioconnectapi.dto.PatientDetails;
import com.dhruza.physioconnectapi.exception.DataNotFoundException;
import com.dhruza.physioconnectapi.exception.UniqueConstraintException;
import com.dhruza.physioconnectapi.model.Patient;
import com.dhruza.physioconnectapi.model.Practitioner;
import com.dhruza.physioconnectapi.repository.PatientRepository;
import com.dhruza.physioconnectapi.repository.PractitionerRepository;
import com.dhruza.physioconnectapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PractitionerRepository practitionerRepository;
    private final PatientRepository patientRepository;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final SignInService patientSignInService;
    private final Random random = new Random();

    @Override
    @Transactional
    public Patient add(Patient patient) {
        final Optional<Practitioner> practitioner = practitionerRepository.findById(
                patient.getPractitioner().getId());
        patient.setPractitioner(practitioner.orElseThrow(() -> new DataNotFoundException(
                "Practitioner with id " + patient.getPractitioner().getId() + " not found!"
        )));

        final String registrationCode = String.valueOf(random.nextInt(900000) + 100000);
        patient.setPassword("");
        patient.setRegistrationCode(registrationCode);
        patient.setRegistrationComplete(false);
        patient.getRoles().add(roleRepository.findByRoleType(RoleType.PATIENT));

        try {
            final Patient savedPatient = patientRepository.save(patient);

            patientSignInService.sendRegistrationEmail(patient.getEmail(), registrationCode);
            return savedPatient;
        } catch (DataIntegrityViolationException e){
            throw new UniqueConstraintException("Patient with that email already exists");
        }
    }

    @Override
    public Set<PatientDetails>
    findWithNextVisitAndActivePlanCount(Long practitionerId) {
       return patientRepository
                .getAllPatientWithDetails(practitionerId);
    }

    @Override
    public PatientDetails
    findPatientWithDetails(Long patientId) {
        return patientRepository.findPatientById(patientId);
    }
}
