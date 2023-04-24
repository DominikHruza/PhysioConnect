package com.dhruza.physioconnectapi.dto.mapping;

import com.dhruza.physioconnectapi.dto.PractitionerResponse;
import com.dhruza.physioconnectapi.model.Practitioner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PractitionerMapper {

    PractitionerResponse convertToDto(Practitioner p);
}
