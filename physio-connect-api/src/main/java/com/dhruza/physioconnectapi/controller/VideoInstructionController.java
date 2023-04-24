package com.dhruza.physioconnectapi.controller;

import com.dhruza.physioconnectapi.dto.VideoUploadResponse;
import com.dhruza.physioconnectapi.services.StorageService;
import com.dhruza.physioconnectapi.services.VideoStreamingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/video-instructions")
public class VideoInstructionController {

    private final StorageService videoStorageService;
    private final VideoStreamingService videoStreamingService;

    public VideoInstructionController( @Qualifier("VideoStorageService") StorageService storageService,
                                       VideoStreamingService videoStreamingService) {
        this.videoStorageService = storageService;
        this.videoStreamingService = videoStreamingService;
}

    @GetMapping(value = "/{title}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String title) {
        return videoStreamingService.streamVideo(title);

    }

    @PostMapping("/upload")
    public ResponseEntity<VideoUploadResponse> handleFileUpload(@RequestParam("videoInstructions") MultipartFile videoInstructions) {
        final String s = videoStorageService.storeFile(videoInstructions);
        final VideoUploadResponse uploadResponse = VideoUploadResponse
                .builder()
                .videoUrl(s)
                .build();
        return new ResponseEntity<>(uploadResponse, HttpStatus.CREATED);
    }
}
