package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.exception.StorageFileException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@Qualifier("VideoStorageService")
public class VideoStorageService implements StorageService{

    @Override
    public String storeFile(MultipartFile videoFile) {
        try {
            Path videoDirPath = Paths.get("assets/videos");
            videoDirPath = Files.createDirectories(videoDirPath);

            final Path documentFileName = Paths.get(new Date().getTime() + "-" + videoFile.getOriginalFilename());
            final Path filePath = videoDirPath.resolve(documentFileName);
            final byte[] documentContent = videoFile.getBytes();

            Files.write(filePath, documentContent);
            return filePath.toString();

        } catch (Exception e) {
            throw new StorageFileException(e);
        }
    }

    @Override
    public Resource fetchFile(String title) {
        ResourceLoader resourceLoader = new FileSystemResourceLoader();
        String basePath = Paths.get("").toAbsolutePath().toString();
        return resourceLoader.getResource("/assets/videos/" + title);
    }
}
