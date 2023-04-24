package com.dhruza.physioconnectapi.dto.mapping;

import com.dhruza.physioconnectapi.dto.AddPatientRequest;
import com.dhruza.physioconnectapi.dto.PatientResponse;
import com.dhruza.physioconnectapi.dto.PatientWithPractitionerResponse;
import com.dhruza.physioconnectapi.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PractitionerMapper.class})
public interface PatientMapper {

    @Mapping(source = "dto.practitionerId", target = "practitioner.id")
    Patient convertFromDto(AddPatientRequest dto);

    @Mapping(source = "patient.practitioner.id", target = "practitioner.id")
    @Mapping(source = "patient.practitioner.firstname", target = "practitioner.firstname")
    @Mapping(source = "patient.practitioner.lastname", target = "practitioner.lastname")
    @Mapping(source = "patient.practitioner.email", target = "practitioner.email")
    PatientWithPractitionerResponse convertToDto(Patient patient);

    Set<PatientResponse> convertToDtoList(Set<Patient> patients);
}
