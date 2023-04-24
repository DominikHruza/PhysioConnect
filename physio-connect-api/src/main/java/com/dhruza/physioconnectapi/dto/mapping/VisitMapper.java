package com.dhruza.physioconnectapi.dto.mapping;

import com.dhruza.physioconnectapi.dto.VisitRequest;
import com.dhruza.physioconnectapi.dto.VisitResponse;
import com.dhruza.physioconnectapi.model.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PractitionerMapper.class, PatientMapper.class})
public interface VisitMapper {
    Set<VisitResponse> convertToDto(Set<Visit> visits);
    VisitResponse convertToDto(Visit visit);

    @Mapping(source = "dto.practitionerId", target = "practitioner.id")
    @Mapping(source = "dto.patientId", target = "patient.id")
    Visit convertFromDto(VisitRequest dto);
}
