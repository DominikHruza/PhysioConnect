package com.dhruza.physioconnectapi.controller;

import com.dhruza.physioconnectapi.auth.model.UserRegistration;
import com.dhruza.physioconnectapi.auth.service.SignInService;
import com.dhruza.physioconnectapi.dto.AddPatientRequest;
import com.dhruza.physioconnectapi.dto.PatientDetails;
import com.dhruza.physioconnectapi.dto.PatientWithPractitionerResponse;
import com.dhruza.physioconnectapi.dto.mapping.PatientMapper;
import com.dhruza.physioconnectapi.model.Patient;
import com.dhruza.physioconnectapi.services.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/patient")
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final PatientMapper patientMapper;
    private final SignInService patientSignInService;

    @GetMapping("/all/next-visit-and-plan-count")
    ResponseEntity<Set<PatientDetails>> getPatientsWithNextVisitAndActivePlanCount(
            @RequestParam(name = "practitionerId") Long practitionerId){
        final Set<PatientDetails> patientCollectionResponse = patientService
                .findWithNextVisitAndActivePlanCount(practitionerId);
        return new ResponseEntity<>(patientCollectionResponse, HttpStatus.OK);
    }

    @GetMapping("/{patientId}")
    ResponseEntity<PatientDetails> getPatientDetails(
            @PathVariable(name = "patientId") Long patientId){
        final PatientDetails patientResponse = patientService
                .findPatientWithDetails(patientId);
        return new ResponseEntity<>(patientResponse, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<PatientWithPractitionerResponse> add(
            @Valid @RequestBody AddPatientRequest patientDto){
        final Patient addedPatient = patientService.add(
                patientMapper.convertFromDto(patientDto));

        return new ResponseEntity<>(patientMapper.convertToDto(addedPatient), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    ResponseEntity<Void> add(@Valid @RequestBody UserRegistration userRegistration){
        patientSignInService.registerUser(userRegistration);
        return ResponseEntity.ok().build();
    }
}
