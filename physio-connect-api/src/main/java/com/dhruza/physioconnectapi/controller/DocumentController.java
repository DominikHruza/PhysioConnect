package com.dhruza.physioconnectapi.controller;

import com.dhruza.physioconnectapi.dto.DocumentResource;
import com.dhruza.physioconnectapi.dto.DocumentResponse;
import com.dhruza.physioconnectapi.dto.mapping.DocumentMapper;
import com.dhruza.physioconnectapi.dto.validation.ValidDocument;
import com.dhruza.physioconnectapi.facade.DocumentFacade;
import com.dhruza.physioconnectapi.services.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Validated
@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;
    private final DocumentFacade documentFacade;

    public DocumentController(DocumentService documentService,
                              DocumentMapper documentMapper,
                              DocumentFacade documentFacade) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
        this.documentFacade = documentFacade;
    }

    @GetMapping("/all/{patientId}")
    ResponseEntity<Set<DocumentResponse>> getAllByPatientId(@PathVariable("patientId") Long patientId){
        final Set<DocumentResponse> documentDtos = documentMapper.convertToDto(documentService
                .findPatientDocuments(patientId));

       return new ResponseEntity<>(documentDtos, HttpStatus.OK);
    }

    @PostMapping("/upload/{patientId}")
    ResponseEntity<DocumentResponse> saveDocument(
            @ValidDocument @RequestParam("document") MultipartFile document,
            @PathVariable("patientId") Long patientId){
        final DocumentResponse documentDto = documentMapper.convertToDto(
                documentFacade.uploadDocument(patientId, document));

        return new ResponseEntity<>(documentDto, HttpStatus.CREATED);
    }

    @GetMapping("/download/{documentId}")
    ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId){
        final DocumentResource documentResource = documentFacade
                .downloadDocument(documentId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/" + documentResource.getFileExtension()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;" + "filename=" + documentResource.getFileName())
                .body(documentResource.getByteArrayResource());
    }
}
