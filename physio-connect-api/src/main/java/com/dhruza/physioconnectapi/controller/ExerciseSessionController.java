package com.dhruza.physioconnectapi.controller;

import com.dhruza.physioconnectapi.dto.AddExerciseSessionRequest;
import com.dhruza.physioconnectapi.dto.ExerciseSessionResponse;
import com.dhruza.physioconnectapi.dto.mapping.ExerciseSessionMapper;
import com.dhruza.physioconnectapi.services.ExerciseSessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;


@AllArgsConstructor
@RestController
@RequestMapping("/api/exercise-session")
public class ExerciseSessionController {

    private final ExerciseSessionService exerciseSessionService;
    private final ExerciseSessionMapper exerciseSessionMapper;

    @GetMapping("/all/{patientId}")
    ResponseEntity<Set<ExerciseSessionResponse>> getAllByPatientId(@PathVariable("patientId") Long patientId){
        final Set<ExerciseSessionResponse> sessionDtos = exerciseSessionMapper.convertToDto(
                exerciseSessionService.getAll(patientId));

        return ResponseEntity.ok().body(sessionDtos);
    }

    @GetMapping("/all/plan/{planId}")
    ResponseEntity<Set<ExerciseSessionResponse>> getAllByPlanIdId(@PathVariable("planId") Long planId){
        final Set<ExerciseSessionResponse> sessionDtos = exerciseSessionMapper.convertToDto(
                exerciseSessionService.getAllByPlan(planId));

        return ResponseEntity.ok().body(sessionDtos);
    }

    @PostMapping
    ResponseEntity<ExerciseSessionResponse> addPlan(@RequestBody AddExerciseSessionRequest dto){
        final ExerciseSessionResponse exerciseSessionResponse = exerciseSessionMapper.convertToDto(
                exerciseSessionService.addNew(
                        exerciseSessionMapper.convertFromDto(dto)));

        return ResponseEntity.created(URI.create("/api/exercise-session"))
                .body(exerciseSessionResponse);
    }
}
