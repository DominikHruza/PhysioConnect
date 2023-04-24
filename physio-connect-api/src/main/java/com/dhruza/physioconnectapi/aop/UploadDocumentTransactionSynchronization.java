package com.dhruza.physioconnectapi.aop;

import com.dhruza.physioconnectapi.exception.StorageFileException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Aspect
@Component
@Slf4j
public class UploadDocumentTransactionSynchronization implements TransactionSynchronization {

    private Path uploadedDocumentPath;

    @Before(value = "execution(" +
            "public com.dhruza.physioconnectapi.model.Document " +
            "com.dhruza.physioconnectapi.services." +
            "DocumentService.addDocument(..)) && args(patientId, filePath)", argNames = "patientId, filePath")
    public void registerTransactionSynchronization(Long patientId, String filePath) {
        uploadedDocumentPath = Paths.get(filePath);
        TransactionSynchronizationManager.registerSynchronization(this);
    }

    @Override
    public void afterCompletion(int status) {
        rollbackFileSystemWriteOnDBRollback(status);
    }

    private void rollbackFileSystemWriteOnDBRollback(int status) {
        try {
            if (STATUS_ROLLED_BACK == status && Files.deleteIfExists(uploadedDocumentPath)){
                log.info("Rollback of document file "
                        + uploadedDocumentPath.toString()+ " after failed database transaction");
            }
        } catch (IOException e) {
            log.info(e.getMessage(), e);
            throw new StorageFileException("Something went wrong during file upload");
        }
    }
}
