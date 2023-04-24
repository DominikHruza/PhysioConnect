package com.dhruza.physioconnectapi.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VideoStreamingServiceImpl implements VideoStreamingService {

    @Qualifier("VideoStorageService")
    private final StorageService videoStorageService;

    public VideoStreamingServiceImpl(StorageService videoStorageService) {
        this.videoStorageService = videoStorageService;
    }

    @Override
    public Mono<Resource> streamVideo(String title) {
       return Mono.fromSupplier(() -> videoStorageService.fetchFile(title));
    }
}
