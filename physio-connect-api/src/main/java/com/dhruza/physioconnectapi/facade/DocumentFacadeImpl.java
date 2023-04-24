package com.dhruza.physioconnectapi.facade;

import com.dhruza.physioconnectapi.dto.DocumentResource;
import com.dhruza.physioconnectapi.exception.StorageFileException;
import com.dhruza.physioconnectapi.model.Document;
import com.dhruza.physioconnectapi.services.DocumentService;
import com.dhruza.physioconnectapi.services.StorageService;
import com.dhruza.physioconnectapi.util.StorageFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class DocumentFacadeImpl implements DocumentFacade {
    
    private final StorageService storageService;
    private final DocumentService documentService;

    public DocumentFacadeImpl(@Qualifier("DocumentStorageService") StorageService storageService, 
                              DocumentService documentService) {
        this.storageService = storageService;
        this.documentService = documentService;
    }
    
    @Override
    public Document uploadDocument(Long patientId, MultipartFile file) {
        String filePath = storageService.storeFile(file);

        return documentService.addDocument(patientId, filePath,  file.getOriginalFilename());
    }

    @Override
    public DocumentResource downloadDocument(Long documentId) {
        Document document = documentService.findById(documentId);
        final String urlLocation = document.getUrlLocation();
        Resource resource = storageService.fetchFile(urlLocation);

        return DocumentResource.builder()
                .byteArrayResource(createByteArrayResource(resource))
                .fileName(StorageFileUtils.getFileName(urlLocation))
                .fileExtension(StorageFileUtils.getFileExtensionType(urlLocation))
                .build();
    }

    private ByteArrayResource createByteArrayResource(Resource resource){
        try(var is = resource.getInputStream();){
          var documentBytes = is.readAllBytes();
          return new ByteArrayResource(documentBytes);
        } catch(IOException e){
            log.info(e.getMessage(), e);
            throw new StorageFileException("Something went wrong during file download!");
        }
    }
}
