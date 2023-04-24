package com.dhruza.physioconnectapi.controller;

import com.dhruza.physioconnectapi.auth.model.SecurityUser;
import com.dhruza.physioconnectapi.dto.WorkoutPlanRequest;
import com.dhruza.physioconnectapi.dto.WorkoutPlanResponse;
import com.dhruza.physioconnectapi.dto.WorkoutPlanResponseNoExrx;
import com.dhruza.physioconnectapi.dto.mapping.WorkoutPlanMapper;
import com.dhruza.physioconnectapi.services.WorkoutPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/api/workout-plan")
@AllArgsConstructor
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;
    private final WorkoutPlanMapper workoutPlanMapper;

    @GetMapping("/all/{patientId}")
    ResponseEntity<Set<WorkoutPlanResponseNoExrx>> getAllForPatient(@PathVariable("patientId") Long patientId){
        final Set<WorkoutPlanResponseNoExrx> workoutPlanResponses = workoutPlanMapper
                .convertToDtoNoExrx(workoutPlanService
                        .findAll(patientId));
        return ResponseEntity.ok().body(workoutPlanResponses);
    }

    @GetMapping("/active/{patientId}")
    ResponseEntity<Set<WorkoutPlanResponse>> getActivePlan(
            @PathVariable("patientId") Long patientId,
            Authentication authentication){
        final Set<WorkoutPlanResponse> workoutPlanResponses = workoutPlanMapper
                .convertToDto(workoutPlanService
                        .findAllActivePlans(patientId));



        return new ResponseEntity<>(workoutPlanResponses, HttpStatus.OK);
    }

    @GetMapping("/exercises/{planId}")
    ResponseEntity<WorkoutPlanResponse> getPlanWithExercises(@PathVariable("planId") Long planId){
        final WorkoutPlanResponse workoutPlanResponse = workoutPlanMapper
                .convertToDto(workoutPlanService.findAllByPlanId(planId));
        return ResponseEntity.ok(workoutPlanResponse);
    }

    @PostMapping
    ResponseEntity<WorkoutPlanResponse> addPlan(@RequestBody WorkoutPlanRequest planDto){
        final WorkoutPlanResponse workoutPlanResponse = workoutPlanMapper
                .convertToDto(workoutPlanService.savePlan(
                        workoutPlanMapper.convertFromDto(planDto)
        ));
        return ResponseEntity.created(URI.create("/api/workout/plan"))
                .body(workoutPlanResponse);
    }

    @PutMapping("/{planId}")
    ResponseEntity<WorkoutPlanResponse> updatePlan(@RequestBody WorkoutPlanRequest planDto, @PathVariable Long planId){
        final WorkoutPlanResponse workoutPlanResponse = workoutPlanMapper.convertToDto(workoutPlanService.updatePlan(
                workoutPlanMapper.convertFromDto(planDto), planId));
        return ResponseEntity.ok().body(workoutPlanResponse);
    }

    @PutMapping("/{planId}/status")
    ResponseEntity<Void> changeStatus(@PathVariable Long planId, @RequestParam Boolean isActive){
        workoutPlanService.updateStatus(planId, isActive);
        return ResponseEntity.ok().build();
    }
}
