package com.dhruza.physioconnectapi.dto.mapping;

import com.dhruza.physioconnectapi.dto.WorkoutPlanRequest;
import com.dhruza.physioconnectapi.dto.WorkoutPlanResponse;
import com.dhruza.physioconnectapi.dto.WorkoutPlanResponseNoExrx;
import com.dhruza.physioconnectapi.model.WorkoutPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {ExerciseMapper.class})
public interface WorkoutPlanMapper {

    @Mapping(source = "plan.practitioner.id", target = "practitionerId")
    @Mapping(source = "plan.patient.id", target = "patientId")
    Set<WorkoutPlanResponse> convertToDto(Set<WorkoutPlan> plan);

    @Mapping(source = "plan.practitioner.id", target = "practitionerId")
    @Mapping(source = "plan.patient.id", target = "patientId")
    WorkoutPlanResponse convertToDto(WorkoutPlan plan);

    @Mapping(source = "plan.practitioner.id", target = "practitionerId")
    @Mapping(source = "plan.patient.id", target = "patientId")
    Set<WorkoutPlanResponseNoExrx> convertToDtoNoExrx(Set<WorkoutPlan> plan);

    @Mapping(source = "dto.practitionerId", target = "practitioner.id")
    @Mapping(source = "dto.patientId", target = "patient.id")
    WorkoutPlan convertFromDto(WorkoutPlanResponse dto);

    @Mapping(source = "dto.practitionerId", target = "practitioner.id")
    @Mapping(source = "dto.patientId", target = "patient.id")
    WorkoutPlan convertFromDto(WorkoutPlanRequest dto);
}

