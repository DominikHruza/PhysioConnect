package com.dhruza.physioconnectapi.util;

import com.dhruza.physioconnectapi.exception.StorageFileException;

import java.nio.file.Paths;
import java.util.Optional;

public class StorageFileUtils {

    private StorageFileUtils() {}

    public static String getFileName(String filePath){
        return Paths.get(filePath).getFileName().toString();
    }

    public static String getFileExtensionType(String filePath){
        final String filename = getFileName(filePath);
        final Optional<String> extension = Optional.of(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));

        return extension.orElseThrow(
                () -> new StorageFileException("Invalid filename format"));
    }
}
