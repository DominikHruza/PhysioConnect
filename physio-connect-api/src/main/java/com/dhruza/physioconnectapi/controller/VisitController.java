package com.dhruza.physioconnectapi.controller;

import com.dhruza.physioconnectapi.dto.VisitRequest;
import com.dhruza.physioconnectapi.dto.VisitResponse;
import com.dhruza.physioconnectapi.dto.mapping.VisitMapper;
import com.dhruza.physioconnectapi.services.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("api/visit")
public class VisitController {

    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @GetMapping("/all/patient/{patientId}")

    ResponseEntity<Set<VisitResponse>> getAllByPatientId(@PathVariable Long patientId){
        final Set<VisitResponse> visitResponses = visitMapper.convertToDto(
                visitService.getAllByPatientId(patientId));
        return ResponseEntity.ok().body(visitResponses);

    }

    @GetMapping("/all/practitioner/{practitionerId}")
    ResponseEntity<Set<VisitResponse>> getAllByPracitionerId(@PathVariable Long practitionerId){
        final Set<VisitResponse> visitResponses = visitMapper.convertToDto(
                visitService.getAllByPractitionerId(practitionerId));
        return ResponseEntity.ok().body(visitResponses);
    }

    @PostMapping
    ResponseEntity<VisitResponse> addNewVisit(@RequestBody VisitRequest visit){
        final VisitResponse visitResponse = visitMapper.convertToDto(
                visitService.addVisit(visitMapper.convertFromDto(visit)));
        return ResponseEntity.ok().body(visitResponse);
    }

    @PutMapping("/reschedule")
    ResponseEntity<VisitResponse> rescheduleVisit(@RequestBody VisitRequest visit){
        final VisitResponse visitResponse = visitMapper.convertToDto(
                visitService.rescheduleVisit(visitMapper.convertFromDto(visit)));
        return ResponseEntity.ok().body(visitResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<VisitResponse> deleteVisit(@PathVariable Long id){
        visitService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }
}
