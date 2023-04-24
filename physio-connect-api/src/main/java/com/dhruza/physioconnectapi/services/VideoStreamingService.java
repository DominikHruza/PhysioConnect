package com.dhruza.physioconnectapi.services;

import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

public interface VideoStreamingService {
    Mono<Resource> streamVideo(String title);
}
