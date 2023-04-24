package com.dhruza.physioconnectapi.services;


import com.dhruza.physioconnectapi.exception.StorageFileException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@Qualifier("DocumentStorageService")
public class DocumentStorageService implements StorageService {
    @Override
    public String storeFile(MultipartFile file) {
        try {
            final Path filePath = createFilePath(file);
            final byte[] documentContent = file.getBytes();
            Files.write(filePath, documentContent);
            return filePath.toString();
        } catch (IOException e) {
            throw new StorageFileException(e);
        }
    }

    @Override
    public Resource fetchFile(String filePath) {
        System.out.println("Dokument: " + filePath);
        ResourceLoader resourceLoader = new FileSystemResourceLoader();
        if(!Files.exists(Paths.get(filePath))){
            throw new StorageFileException("File not found!");
        }
        return resourceLoader.getResource(filePath);
    }

    private Path createFilePath(MultipartFile file) {
        try {
            Path documentDirPath = Paths.get("assets/documents");
            documentDirPath = Files.createDirectories(documentDirPath);
            final Path documentFileName = Paths.get(new Date().getTime() + "-"
                    + file.getOriginalFilename());
            return documentDirPath.resolve(documentFileName);

        } catch (IOException e) {
            throw new StorageFileException(e);
        }
    }
}
