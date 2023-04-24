package com.dhruza.physioconnectapi.dto.mapping;

import com.dhruza.physioconnectapi.dto.DocumentResponse;
import com.dhruza.physioconnectapi.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "document.patient.id", target = "dto.patientId")
    Set<DocumentResponse> convertToDto(Set<Document> document);

    @Mapping(source = "document.patient.id", target = "patientId")
    DocumentResponse convertToDto(Document document);
}
