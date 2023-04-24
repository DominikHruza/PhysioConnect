package com.dhruza.physioconnectapi.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String storeFile(MultipartFile file);
    Resource fetchFile(String filePath);
}
